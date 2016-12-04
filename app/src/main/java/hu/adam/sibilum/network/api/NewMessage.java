package hu.adam.sibilum.network.api;

import hu.adam.sibilum.Utils;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.Param;

public class NewMessage extends Api {

    public NewMessage(OnApiResult onApiResultListener, int channel, int user, String message) {
        super(Api.API_MESSAGE_NEW, onApiResultListener,
                new Param<String, String>("channel", String.valueOf(channel)),
                new Param<String, String>("user", String.valueOf(user)),
                new Param<String, String>("message", Utils.toUtf8(message))
        );
    }

}
