package com.abilix.myapp.socket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.abilix.myapp.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MulticastSocketUtil {

    public static final int MEMBER_STATE_ADDED = 0x01;
    public static final int MEMBER_STATE_DELETED = 0x02;

    private static final String mGroupIP = "224.1.1.1";
    private static final int mPort = 8000;
    private static final int mPacketDataLength = 4 * 1024; //KB
    private MulticastSocket mMulticastSocket;
    private InetAddress mGroupAddress = null;
    private MulticastSocketListener mMulticastSocketListener = null;
    private String mNickName;

    private HandlerThread mHandlerThread;
    private ServerHandler mHandler;

    private ReceiveThread mReceiveThread;

    private static final long mHeartbeatDelay = 200; //ms
    private static final long mHeartbeatPeriod = 1000; //ms
    private Timer mHeartbeatTimer;

    private Map<String, UserInfo> mGroupMembers = new HashMap<>();

    public MulticastSocketUtil() {
        //初始化MulticastSocket
        try {
            mMulticastSocket = new MulticastSocket(mPort);
            mGroupAddress = InetAddress.getByName(mGroupIP);
            if (!mGroupAddress.isMulticastAddress()) {
                Logger.e("MulticastSocketUtil: " + mGroupAddress.getHostAddress() + " is not MulticastAddress!");
            }
            mMulticastSocket.joinGroup(mGroupAddress);
            //mMulticastSocket.setTimeToLive(1);
            //mMulticastSocket.setLoopbackMode(false);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
        }
        //初始化Handler
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        mHandler = new ServerHandler(mHandlerThread.getLooper());
        //初始化接收线程
        mReceiveThread = new ReceiveThread("ReceiveThread");
        mReceiveThread.start();
        mNickName = NetworkUtil.getLocalIPAddress();
        //初始化心跳Timer
        mHeartbeatTimer = new Timer("Heartbeat");
        mHeartbeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = mNickName + "#" + "isAlive";
                sendMulticastSocket(msg.getBytes());
            }
        }, mHeartbeatDelay, mHeartbeatPeriod);
    }

    public void setNickName(String name) {
        mNickName = name;
    }

    public void setMulticastSocketListener(MulticastSocketListener listener) {
        mMulticastSocketListener = listener;
    }

    public synchronized void sendMulticastSocket(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, mGroupAddress, mPort);
        if (mHandler != null) {
            mHandler.obtainMessage(MSG_SEND_DATA, packet).sendToTarget();
        }
    }

    public synchronized void sendSingleSocket(UserInfo toUser, byte[] data) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, toUser.getAddress());
            if (mHandler != null) {
                mHandler.obtainMessage(MSG_SEND_DATA, packet).sendToTarget();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        mMulticastSocketListener = null;
        if (mHeartbeatTimer != null) {
            mHeartbeatTimer.cancel();
            mHeartbeatTimer = null;
        }
        if (mReceiveThread != null) {
            mReceiveThread.quit();
            mReceiveThread = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread = null;
        }
        if (mMulticastSocket != null) {
            mMulticastSocket.close();
            mMulticastSocket = null;
        }
        if (mGroupMembers != null) {
            mGroupMembers.clear();
            mGroupMembers = null;
        }
    }

    //接收线程类
    private class ReceiveThread extends Thread {
        private boolean mRunning;
        ReceiveThread(String listeningThread) {
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
            byte[] data = new byte[mPacketDataLength];
            DatagramPacket packet = new DatagramPacket(data, 0, data.length);
            if (mMulticastSocket != null) {
                try {
                    while (mRunning) {
                        packet.setData(data, 0, data.length);
                        mMulticastSocket.receive(packet);
                        String fromIp = packet.getAddress().getHostAddress();
                        String msg = new String(packet.getData(), packet.getOffset(),packet.getLength(), Charset.defaultCharset());
                        UserInfo fromUser;
                        if (msg.contains("#")) {
                            String[] userMsg = msg.split("#");
                            if (mGroupMembers.containsKey(fromIp)) {
                                fromUser = mGroupMembers.get(fromIp);
                                fromUser.setName(userMsg[0]);
                            } else {
                                fromUser = new UserInfo(userMsg[0], packet);
                                mGroupMembers.put(fromIp, fromUser);
                                if (mMulticastSocketListener != null) {
                                    mMulticastSocketListener.onStateChanged(fromUser, MEMBER_STATE_ADDED);
                                }
                            }
                            //遍历所有组成员,更新状态
                            long checkTime = System.currentTimeMillis();
                            Iterator<Map.Entry<String, UserInfo>> iterator = mGroupMembers.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<String, UserInfo> entry = iterator.next();
                                String key = entry.getKey();
                                UserInfo user = entry.getValue();
                                user.setCheckTime(checkTime);
                                if (user.equals(fromUser)) {
                                    user.setName(fromUser.getName());
                                    user.resetLastCheckTime();
                                }
                                mGroupMembers.put(key, user);
                                //删除不在线成员
                                if (!user.isOnline()) {
                                    iterator.remove();
                                    if (mMulticastSocketListener != null) {
                                        mMulticastSocketListener.onStateChanged(user, MEMBER_STATE_DELETED);
                                    }
                                }
                            }
                            continue;
                        }
                        if (mGroupMembers.containsKey(fromIp)) {
                            fromUser = mGroupMembers.get(fromIp);
                        } else {
                            continue;
                        }

                        if (mMulticastSocketListener != null) {
                            byte[] received = new byte[packet.getLength()];
                            System.arraycopy(data, packet.getOffset(), received, 0, packet.getLength());
                            mMulticastSocketListener.onReceived(fromUser, received);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.e(e.getMessage());
                }
            }
        }
    }

    private final static int MSG_SEND_DATA = 0x01;

    //Handler处理类
    private class ServerHandler extends Handler {

        ServerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.d("handleMessage: " + msg.what);
            switch (msg.what) {
                case MSG_SEND_DATA:
                    DatagramPacket packet = (DatagramPacket) msg.obj;
                    try {
                        if (mMulticastSocket != null) {
                            mMulticastSocket.send(packet);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Logger.e(e.getMessage());
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
