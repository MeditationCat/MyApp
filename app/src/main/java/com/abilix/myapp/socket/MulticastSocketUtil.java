package com.abilix.myapp.socket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketUtil {

    private static final String mGroupIP = "224.1.1.1";
    private static final int mPort = 8000;

    private MulticastSocket mMulticastSocket;
    private InetAddress mGroupAddress = null;
    private MulticastSocketListener mMulticastSocketListener = null;
    private ReceiveThread mReceiveThread;
    private HandlerThread mServerThread;
    private ServerHandler mHandler;

    public MulticastSocketUtil() {
        try {
            mMulticastSocket = new MulticastSocket(mPort);
            mMulticastSocket.setTimeToLive(1);
            mGroupAddress = InetAddress.getByName(mGroupIP);
            if (!mGroupAddress.isMulticastAddress()) {
                Logger.e("MulticastSocketUtil: " + mGroupAddress.getHostAddress() + " is not MulticastAddress!");
            }
            mMulticastSocket.joinGroup(mGroupAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mServerThread = new HandlerThread("HandlerThread");
        mServerThread.start();
        mHandler = new ServerHandler(mServerThread.getLooper());

        mReceiveThread = new ReceiveThread("ReceiveThread");
        mReceiveThread.start();

    }

    public void setMulticastSocketListener(MulticastSocketListener listener) {
        mMulticastSocketListener = listener;
    }

    public synchronized void sendMulticastSocket(final byte[] data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, mGroupAddress, mPort);
                try {
                    if (mMulticastSocket != null) {
                        mMulticastSocket.send(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void close() {
        mMulticastSocketListener = null;
        if (mReceiveThread != null) {
            mReceiveThread.quit();
            mReceiveThread = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mServerThread != null) {
            mServerThread.quit();
            mServerThread = null;
        }
        if (mMulticastSocket != null) {
            mMulticastSocket.close();
            mMulticastSocket = null;
        }
    }

    private class ReceiveThread extends Thread {
        private boolean mRunning;
        public ReceiveThread(String listeningThread) {
            super(listeningThread);
        }

        @Override
        public synchronized void start() {
            mRunning = true;
            super.start();
        }

        public synchronized void quit() {
            mRunning = false;
        }

        @Override
        public void run() {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, 0, data.length);
            if (mMulticastSocket != null) {
                try {
                    while (mRunning) {
                        packet.setData(data, 0, data.length);
                        mMulticastSocket.receive(packet);
                        if (mMulticastSocketListener != null) {
                            byte[] received = new byte[packet.getLength()];
                            System.arraycopy(data, packet.getOffset(), received, 0, packet.getLength());
                            mMulticastSocketListener.onReceived(mMulticastSocket, packet, received);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class ServerHandler extends Handler {

        public ServerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.d("handleMessage: " + msg.what);
        }
    }

}
