package hu.adam.sibilum.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.Utils;

/**
 * Created by adam on 2016.11.17..
 */

public class Message {

    private static final String[] sFields = {"id", "channel", "user", "message", "date"};

    private int mId;
    private int mChannel;
    private int mUser;
    private String mMessage;
    private Date mDate;

    public Message(int id, int channel, int user, String message, Date date) {
        mId         = id;
        mChannel    = channel;
        mUser       = user;
        mMessage    = message;
        mDate       = date;
    }

    public int getId() {
        return mId;
    }

    public int getChannel() {
        return mChannel;
    }

    public int getUser() {
        return mUser;
    }

    public String getMessage() {
        return mMessage;
    }

    public Date getDate() {
        return mDate;
    }

    public static Message fromJson(JSONObject json) {

        if( !Utils.isValidModel(json, sFields) )
            return null;

        try {
            return new Message(
                    json.getInt("id"),
                    json.getInt("channel"),
                    json.getInt("user"),
                    Utils.fromUtf8(json.getString("message")),
                    new Date(0)
            );
        } catch (JSONException e) {
            return null;
        }
    }

    public static Message fromString(String str) throws JSONException {
        return Message.fromJson(new JSONObject(str));
    }

    public static List<Message> fromJsonArray(JSONArray array) throws JSONException {
        List<Message> messages = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            messages.add(Message.fromJson(array.getJSONObject(i)));
        }

        return messages;
    }
}
