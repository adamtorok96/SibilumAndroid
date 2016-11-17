package hu.adam.sibilum.network;

/**
 * Created by adam on 2016.11.17..
 */

public class Message {

    private Channel mChannel;
    private User mUser;
    private String mMessage;

    public Message(Channel channel, User user, String message) {
        mChannel    = channel;
        mUser       = user;
        mMessage    = message;
    }

    public Channel getChannel() {
        return mChannel;
    }

    public User getUser() {
        return mUser;
    }

    public String getMessage() {
        return mMessage;
    }
}
