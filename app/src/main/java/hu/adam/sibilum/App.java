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

public class App extends Application implements OnApiResult {

    private static final String SERVER_HOST     = "rick.sch.bme.hu";

    private static App sApp = null;

    private int mBroadcastPort;

    private User mUser;

    private List<User> mUsers;

    public static App get() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;

        mBroadcastPort = Utils.rand(49152, 65535);

        mUsers  = new ArrayList<>();

        Server.start(this, mBroadcastPort);
    }

    public void DownloadUsers() {
        new GetUsers(this).start();
    }

    public User getUser() {
        return mUser;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public User getUserById(int id) {

        for(User user : mUsers) {
            if( user.getId() == id )
                return user;
        }

        return null;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public int getBroadcastPort() {
        return mBroadcastPort;
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
