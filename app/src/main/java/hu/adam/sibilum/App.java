package hu.adam.sibilum;

import android.app.Application;

import java.net.InetSocketAddress;

import hu.adam.sibilum.network.Server;

/**
 * Created by adam on 2016.11.17..
 */

public class App extends Application {

    private static final String SERVER_HOST     = "rick.sch.bme.hu";
    private static final int SERVER_PORT        = 9000;

    private Server mServer;

    @Override
    public void onCreate() {
        super.onCreate();

        mServer = new Server(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
    }

    public Server getServer() {
        return mServer;
    }
}
