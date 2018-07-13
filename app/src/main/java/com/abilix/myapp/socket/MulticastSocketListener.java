package com.abilix.myapp.socket;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

public interface MulticastSocketListener {
    void onReceived(MulticastSocket socket, DatagramPacket packet, byte[] data);
}
