package hu.adam.sibilum;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.User;
import hu.adam.sibilum.network.Server;
import hu.adam.sibilum.network.api.GetUsers;

/**
 * Created by adam on 2016.11.17..
 */

public class App extends Application implements OnApiResult {

    private static final String SERVER_HOST     = "rick.sch.bme.hu";

    private static App sApp = null;

    private User mUser;
    private Server mServer;

    private List<User> mUsers;

    public static App get() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        mServer = new Server();
        mUsers  = new ArrayList<>();
    }

    public void DownloadUsers() {
        new GetUsers(this);
    }

    public Server getServer() {
        return mServer;
    }

    public User getUser() {
        return mUser;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public void onSuccess(String api, String response) {
        JSONObject json;

        try {
            json    = new JSONObject(response);
            mUsers  = User.fromJsonArray(json.getJSONArray("users"));
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onFail(String api) {

    }
}
