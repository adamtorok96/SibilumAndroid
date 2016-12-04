package hu.adam.sibilum.models;

import org.json.JSONException;
import org.json.JSONObject;

import hu.adam.sibilum.Utils;

public class Channel {

    private static final String[] sFields = {"id", "name"};

    private int mId;
    private String mName;

    public Channel(int id, String name) {
        mId     = id;
        mName   = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Channel && ((Channel)obj).mId == mId;
    }

    public static Channel fromJson(JSONObject json) {

        if( !Utils.isValidModel(json, sFields) )
            return null;

        try {
            return new Channel(
                    json.getInt("id"),
                    json.getString("name")
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
