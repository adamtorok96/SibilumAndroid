package hu.adam.sibilum.network.api;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.network.Api;

public class GetChannels extends Api {
    public GetChannels(OnApiResult onApiResultListener) {
        super(Api.API_CHANNEL_LIST, onApiResultListener);
    }
}
