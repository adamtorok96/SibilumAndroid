package hu.adam.sibilum.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import hu.adam.sibilum.Utils;

public class BroadcastMessage extends Thread {

    private static final String HTTP_PREFIX = "GET /";
    private static final String BR_MESSAGE  = "message";
    private static final String BR_CHANNEL  = "channel";
    private static final String BR_USER     = "user";

    private Socket mSocket;

    public BroadcastMessage(Socket socket) {
        mSocket = socket;
    }

    @Override
    public void run() {
        InputStream is;
        OutputStream os;

        try {
            is = mSocket.getInputStream();
            os = mSocket.getOutputStream();
        } catch (IOException e) {
            Utils.Log.error(e); return;
        }

        String request = readRequest(is);

        if( request.startsWith(HTTP_PREFIX + BR_CHANNEL) )
            Utils.Log.info("new ch");
        else if( request.startsWith(HTTP_PREFIX + BR_USER) )
            Utils.Log.info("new user");
        else if( request.startsWith(HTTP_PREFIX + BR_MESSAGE) )
            Utils.Log.info("new msg");
        else Utils.Log.info("unknown br");

        try {
            mSocket.close();
        } catch (IOException e) {
            Utils.Log.error(e);
        }
    }

    private String readRequest(InputStream is) {
        String request = "";

        byte[] buffer = new byte[512];
        int r;

        try {
            while( (r = is.read(buffer)) > 0 ) {
                request += new String(buffer, 0, r);

            }
        } catch (IOException ignored) {

        }

        return request;
    }
}
