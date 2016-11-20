package hu.adam.sibilum;

import android.app.Application;

import hu.adam.sibilum.network.Server;

/**
 * Created by adam on 2016.11.17..
 */

public class App extends Application {

    private static final String SERVER_HOST     = "rick.sch.bme.hu";


    private Server mServer;

    @Override
    public void onCreate() {
        super.onCreate();

        mServer = new Server();
    }

    public Server getServer() {
        return mServer;
    }
}
