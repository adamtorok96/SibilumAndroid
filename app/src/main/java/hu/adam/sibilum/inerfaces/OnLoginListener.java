package hu.adam.sibilum.inerfaces;

/**
 * Created by adam on 2016.11.20..
 */

public interface OnLoginListener {
    void onLoginSuccess(int user, String username);
    void onLoginFail();
}
