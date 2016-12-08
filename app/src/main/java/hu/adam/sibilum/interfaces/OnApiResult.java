package hu.adam.sibilum.interfaces;

public interface OnApiResult {
    void onSuccess(String api, String response);
    void onFail(String api);
}
