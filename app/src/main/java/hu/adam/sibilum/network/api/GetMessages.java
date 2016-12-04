package hu.adam.sibilum.network.api;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.Param;

/**
 * Created by edems on 2016.12.04..
 */

public class GetMessages extends Api {

    public GetMessages(OnApiResult onApiResultListener, int channel) {
        super(Api.API_MESSAGE_LIST, onApiResultListener,
                new Param<String, String>("channel", String.valueOf(channel))
        );
    }

}
