package hu.adam.sibilum.models;

/**
 * Created by adam on 2016.11.17..
 */

public class Channel {

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
}
