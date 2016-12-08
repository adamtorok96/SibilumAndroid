package hu.adam.sibilum.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hu.adam.sibilum.interfaces.OnApiResult;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api extends Thread {

    private static final String SERVER_SCHEMA       = "http";
    private static final String SERVER_HOST         = "rick.sch.bme.hu";
    private static final int SERVER_PORT            = 9000;

    protected static final String API_USER_LIST       = "users";
    protected static final String API_USER_NEW        = "users/new";
    protected static final String API_CHANNEL_LIST    = "channels";
    protected static final String API_CHANNEL_NEW     = "channels/new";
    protected static final String API_MESSAGE_LIST    = "messages";
    protected static final String API_MESSAGE_NEW     = "messages/new";

    private String mApi;
    private OnApiResult mOnApiResultListener;
    private Param<String, String> mParams[];

    public Api(String api, OnApiResult onApiResultListener, Param<String, String>... params) {
        mApi                    = api;
        mOnApiResultListener    = onApiResultListener;
        mParams                 = params;
    }

    @Override
    public void run() {
        try {
            String response = Get(mApi, mParams);

            if( mOnApiResultListener != null )
                mOnApiResultListener.onSuccess(mApi, response);
        } catch (IOException e) {
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
                .host(SERVER_HOST)
                .port(SERVER_PORT)
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
}
