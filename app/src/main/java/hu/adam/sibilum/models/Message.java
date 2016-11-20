package hu.adam.sibilum.models;

import java.sql.Date;

/**
 * Created by adam on 2016.11.17..
 */

public class Message {

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
}
