package hu.adam.sibilum.network.api;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.network.Api;

public class NewMessage extends Api {

    public NewMessage(OnApiResult onApiResultListener, Message message) {
        super(Api.API_MESSAGE_NEW, onApiResultListener, message.toJson());
    }

}
