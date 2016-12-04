package hu.adam.sibilum.models;

import org.json.JSONException;
import org.json.JSONObject;

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
}
