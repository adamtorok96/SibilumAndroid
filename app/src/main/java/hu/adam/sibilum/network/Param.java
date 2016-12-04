package hu.adam.sibilum.network;

/**
 * Created by edems on 2016.12.02..
 */

public class Param<K, V> {

    private K mKey;
    private V mValue;

    public Param(K key) {
        mKey    = key;
        mValue  = null;
    }

    public Param(K key, V value) {
        mKey    = key;
        mValue  = value;
    }

    public boolean hasValue() {
        return mValue != null;
    }

    public K getKey() {
        return mKey;
    }

    public V getValue() {
        return mValue;
    }

}
