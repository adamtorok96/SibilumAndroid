package hu.adam.sibilum.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import hu.adam.sibilum.inerfaces.OnLoginListener;

public class Login extends Thread {

    private String mUsername;
    private OnLoginListener mListener;

    public Login(String username, OnLoginListener listener) {
        mUsername   = username;
        mListener   = listener;
    }

    private void onSuccess(int user) {
        if( mListener != null )
            mListener.onLoginSuccess(user, mUsername);
    }

    private void onFail() {
        if( mListener != null )
            mListener.onLoginFail();
    }

    @Override
    public void run() {
        String response;

        try {
            response = Api.Get("login?name=" + URLEncoder.encode(mUsername, "UTF-8"));
        } catch (IOException e) {
            onFail(); return;
        }

        int user;

        try {
            JSONObject json = new JSONObject(response);
            user = json.getInt("user");
        } catch (JSONException e) {
            onFail(); return;
        }

        onSuccess(user);
    }
}
