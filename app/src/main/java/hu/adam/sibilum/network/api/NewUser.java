package hu.adam.sibilum.network.api;

import hu.adam.sibilum.Utils;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.Param;

public class NewUser extends Api {

    public NewUser(OnApiResult onApiResultListener, String name, int port) {
        super(API_USER_NEW, onApiResultListener,
                new Param<String, String>("name", Utils.toUtf8(name)),
                new Param<String, String>("port", String.valueOf(port))
        );
    }

}
