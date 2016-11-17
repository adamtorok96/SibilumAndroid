package hu.adam.sibilum.network;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import hu.adam.sibilum.interfaces.OnConnectionListener;

/**
 * Created by adam on 2016.11.17..
 */

public class ServerConnect extends Thread {

    private Socket mSocket;
    private SocketAddress mAddress;
    private OnConnectionListener mListener;

    public ServerConnect(SocketAddress address, OnConnectionListener listener) {
        mAddress = address;
        mListener = listener;

        mSocket = new Socket();
    }

    @Override
    public void run() {

        try {
            mSocket.connect(mAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
