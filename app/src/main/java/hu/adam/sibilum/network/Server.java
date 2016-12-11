package hu.adam.sibilum.network;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import hu.adam.sibilum.Utils;

public class Server extends IntentService {

    private static final String EXTRA_PORT = "extra_port";

    private ServerSocket mSocket;

    public static void start(Context context, int port) {
        Intent intent = new Intent(context, Server.class);
        intent.putExtra(EXTRA_PORT, port);

        context.startService(intent);
    }

    public Server() {
        super("Server");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int port = intent.getExtras().getInt(EXTRA_PORT, 0);

        if( port == 0 )
            return;

        try {
            mSocket = new ServerSocket(port);
        } catch (IOException e) {
            Utils.Log.error(e); return;
        }

        Socket socket;

        while( true ) {
            try {
                socket = mSocket.accept();
            } catch (IOException e) {
                Utils.Log.error(e); continue;
            }

            Utils.Log.info("New broadcast!");

            new BroadcastMessage(socket).start();
        }
    }
}
