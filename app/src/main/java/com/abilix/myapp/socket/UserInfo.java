package com.abilix.myapp.socket;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.Objects;

public class UserInfo {
    private String name;
    private String ip;
    private int port;
    private SocketAddress address;

    private static final int CHECK_TIMEOUT = 3 * 1000; //ms
    private boolean online;
    private long lastCheckTime;
    private long checkTime;

    public UserInfo(String name, DatagramPacket packet) {
        this(name, packet.getAddress().getHostAddress(), packet.getPort(), packet.getSocketAddress());
    }

    public UserInfo(String name, String ip, int port, SocketAddress address) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public boolean isOnline() {
        return checkTime - lastCheckTime <= CHECK_TIMEOUT;
    }

    public void resetLastCheckTime() {
        lastCheckTime = checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(address, userInfo.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
