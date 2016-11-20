package hu.adam.sibilum;

import android.app.Application;

import hu.adam.sibilum.models.User;
import hu.adam.sibilum.network.Server;

/**
 * Created by adam on 2016.11.17..
 */

public class App extends Application {

    private static final String SERVER_HOST     = "rick.sch.bme.hu";

    private static App sApp = null;

    private User mUser;
    private Server mServer;

    public static App get() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        mServer = new Server();
    }

    public Server getServer() {
        return mServer;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
