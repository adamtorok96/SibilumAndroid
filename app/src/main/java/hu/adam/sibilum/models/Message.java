package hu.adam.sibilum.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.Utils;

public class Message {

    private static final String[] sFields   = {"id", "channel", "user", "message", "date"};
    private static final int BASE64_FLAGS   = Base64.NO_WRAP | Base64.URL_SAFE;

    public static final int TYPE_TEXT   = 1;
    public static final int TYPE_IMAGE  = 2;

    private int mId;
    private int mChannel;
    private int mUser;
    private int mType;
    private String mMessage;
    private Date mDate;

    public Message(int id, int channel, int user, int type, String message, Date date) {
        mId         = id;
        mChannel    = channel;
        mUser       = user;
        mType       = TYPE_TEXT;
        mType       = type;
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

    public boolean isText() {
        return mType == TYPE_TEXT;
    }

    public boolean isImage() {
        return mType == TYPE_IMAGE;
    }

    public Bitmap getBitmap() {
        byte[] bytes = Base64.decode(mMessage, BASE64_FLAGS);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Message fromJson(JSONObject json) {

        if( !Utils.isValidModel(json, sFields) )
            return null;

        try {
            int type = json.getInt("type");

            return new Message(
                    json.getInt("id"),
                    json.getInt("channel"),
                    json.getInt("user"),
                    type,
                    type == TYPE_TEXT
                        ? Utils.fromUtf8(json.getString("message"))
                        : json.getString("message"),
                    new Date(0)
            );
        } catch (JSONException e) {
            return null;
        }
    }

    public static Message fromString(String str) throws JSONException {
        return Message.fromJson(new JSONObject(str));
    }

    public static Message fromBitmap(int channel, int user, byte[] buffer) {
        return new Message(
                0,
                channel,
                user,
                TYPE_IMAGE,
                Base64.encodeToString(buffer, BASE64_FLAGS),
                null
        );
    }

    public static List<Message> fromJsonArray(JSONArray array) throws JSONException {
        List<Message> messages = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            messages.add(Message.fromJson(array.getJSONObject(i)));
        }

        return messages;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        try {
            json.put("id", mId);
            json.put("channel", mChannel);
            json.put("user", mUser);
            json.put("type", mType);
            json.put("message", mMessage);
            json.put("date", null);
        } catch (JSONException e) {
            return null;
        }

        return json;
    }
}
