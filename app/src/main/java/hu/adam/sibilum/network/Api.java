package hu.adam.sibilum.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static final String ENDPOINT_URL = "http://rick.sch.bme.hu:9000/";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
    }


    public static String Get(String api) throws IOException {
        Request request = new Request.Builder()
                .url(ENDPOINT_URL + api)
                .build();

        Response response = getClient()
                .newCall(request)
                .execute();

        return response.body().string();
    }

    public static String Post(String api, String json) throws IOException {
        Request request = new Request.Builder()
                .url(ENDPOINT_URL + api)
                .post(RequestBody.create(JSON, json))
                .build();

        Response response = getClient()
                .newCall(request)
                .execute();

        return response.body().string();
    }
}
