package hu.adam.sibilum.models;

/**
 * Created by adam on 2016.11.17..
 */

public class User {

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
}
