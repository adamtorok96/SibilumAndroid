package hu.adam.sibilum.network;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by adam on 2016.11.17..
 */

public class Server extends Thread {

    private Socket mSocket;
    private SocketAddress mAddress;

    public Server(SocketAddress address) {
        mAddress = address;
    }

    public boolean connect() {
        mSocket = new Socket();

        try {
            mSocket.connect(mAddress, 3000);

            start();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void run() {
        super.run();
    }

    public boolean login(String username) {
        return false;
    }
}
