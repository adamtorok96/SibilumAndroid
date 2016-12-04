package hu.adam.sibilum.network.api;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;

public class GetUsers extends Api {

    public GetUsers(OnApiResult onApiResultListener) {
        super(Api.API_USER_LIST, onApiResultListener);
    }
}
