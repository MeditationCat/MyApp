package com.abilix.myapp.socket;

public interface MulticastSocketListener {
    void onStateChanged(UserInfo user, int onlineState);
    void onReceived(UserInfo user, byte[] data);
}
