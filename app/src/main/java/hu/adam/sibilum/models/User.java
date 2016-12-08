package hu.adam.sibilum.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.Utils;

public class User {

    private static final String[] sFields = {"id", "name"};

    private int mId;
    private String mName;

    public User(int id, String name) {
        mId     = id;
        mName   = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public static User fromJson(JSONObject json) {

        if( !Utils.isValidModel(json, sFields) )
            return null;

        try {
            return new User(
                    json.getInt("id"),
                    json.getString("name")
            );
        } catch (JSONException e) {
            return null;
        }
    }

    public static List<User> fromJsonArray(JSONArray array) throws JSONException {
        List<User> users = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            users.add(User.fromJson(array.getJSONObject(i)));
        }

        return users;
    }

    public static User fromString(String str) throws JSONException {
        return User.fromJson(new JSONObject(str));
    }
}
