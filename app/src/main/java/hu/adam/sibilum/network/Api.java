package hu.adam.sibilum.network;

import android.preference.PreferenceManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hu.adam.sibilum.App;
import hu.adam.sibilum.Utils;
import hu.adam.sibilum.interfaces.OnApiResult;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api extends Thread {

    private static final String PREF_ADDRESS        = "server_address";
    private static final String PREF_PORT           = "server_port";

    private static final String SERVER_SCHEMA       = "http";
    private static final String SERVER_HOST         = "rick.sch.bme.hu";
    private static final int SERVER_PORT            = 9000;

    private static final int METHOD_GET             = 0;
    private static final int METHOD_POST            = 1;

    public static final String API_USER_LIST       = "users";
    public static final String API_USER_NEW        = "login";
    public static final String API_CHANNEL_LIST    = "channels";
    public static final String API_CHANNEL_NEW     = "channels/new";
    public static final String API_MESSAGE_LIST    = "messages";
    public static final String API_MESSAGE_NEW     = "messages/new";

    private int mMethod = METHOD_GET;
    private String mApi;
    private OnApiResult mOnApiResultListener;

    private Param<String, String> mParams[];
    private JSONObject mData;

    private static String getHost() {
        return PreferenceManager.getDefaultSharedPreferences(App.get()).getString(PREF_ADDRESS, SERVER_HOST);
    }

    private static int getPort() {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(App.get()).getString(PREF_PORT, String.valueOf(SERVER_PORT)));
    }

    public Api(String api, OnApiResult onApiResultListener, Param<String, String>... params) {
        mApi                    = api;
        mOnApiResultListener    = onApiResultListener;
        mParams                 = params;
    }

    public Api(String api, OnApiResult onApiResultListener, JSONObject data) {
        mApi                    = api;
        mOnApiResultListener    = onApiResultListener;
        mData                   = data;
        mMethod                 = METHOD_POST;
    }

    @Override
    public void run() {
        try {
            String response = mMethod == METHOD_GET
                    ? Get(mApi, mParams)
                    : Post(mApi, mData);

            Utils.Log.info("Response: "+ response);

            if( mOnApiResultListener != null )
                mOnApiResultListener.onSuccess(mApi, response);

        } catch (IOException e) {
            Utils.Log.error(e);

            if( mOnApiResultListener != null )
                mOnApiResultListener.onFail(mApi);
        }
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
    }

    private static String Get(String api, Param<String, String> params[]) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(SERVER_SCHEMA)
                .host(getHost())
                .port(getPort())
                .addPathSegments(api);

        for(Param<String, String> param : params) {
            urlBuilder.addQueryParameter(param.getKey(), param.getValue());
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        Response response = getClient()
                .newCall(request)
                .execute();

        return response.body().string();
    }

    private static String Post(String api, JSONObject data) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(SERVER_SCHEMA)
                .host(getHost())
                .port(getPort())
                .addPathSegments(api);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                data.toString()
        );

        Utils.Log.info("SEND: "+ data.toString());

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .post(requestBody)
                .build();

        Response response = getClient()
                .newCall(request)
                .execute();

        return response.body().string();
    }
}
