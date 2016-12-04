package hu.adam.sibilum.network.api;

import hu.adam.sibilum.Utils;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.Param;


public class NewChannel extends Api {

    public NewChannel(OnApiResult onApiResultListener, String name) {
        super(Api.API_CHANNEL_NEW, onApiResultListener,
                new Param<String, String>("name", Utils.toUtf8(name)));
    }

}
