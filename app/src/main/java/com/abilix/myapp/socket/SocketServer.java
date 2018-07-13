package com.abilix.myapp.socket;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private ServerSocket mServerSocket;
    private static int mServerListeningPort = 0;

    private ListeningThread mListeningThread;

    private HandlerThread mServerThread;
    private ServerHandler mServerHandler;

    public SocketServer(int port) {
        try {
            mServerListeningPort = port;
            mServerSocket = new ServerSocket(mServerListeningPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mServerThread = new HandlerThread("HandlerThread");
        mServerThread.start();
        mServerHandler = new ServerHandler(mServerThread.getLooper());
    }

    public void start() {
        mListeningThread = new ListeningThread("ListeningThread");
        mListeningThread.start();
    }

    public void close() {
        try {
            if (mListeningThread != null) {
                mListeningThread.quit();
                mListeningThread = null;
            }
            if (mServerHandler != null) {
                mServerHandler.removeCallbacksAndMessages(null);
                mServerHandler = null;
            }
            if (mServerThread != null) {
                mServerThread.quit();
                mServerThread = null;
            }
            if (mServerSocket != null) {
                mServerSocket.close();
                mServerSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ListeningThread extends Thread {
        private boolean mRunning;
        public ListeningThread(String listeningThread) {
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
            Socket socket;
            OutputStream outputStream = null;
            InputStream inputStream = null;
            byte[] receiveData;
            if (mServerSocket != null) {
                while (mRunning) {
                    try {
                        socket = mServerSocket.accept();
                        outputStream = socket.getOutputStream();
                        inputStream = socket.getInputStream();
                        int length = inputStream.available();
                        if (length > 0) {
                            receiveData = new byte[length];
                            inputStream.read(receiveData);
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
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
