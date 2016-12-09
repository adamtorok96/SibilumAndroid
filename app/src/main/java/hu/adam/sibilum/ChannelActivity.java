package hu.adam.sibilum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.adapter.MessageAdapter;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.network.api.GetMessages;


public class ChannelActivity extends AppCompatActivity implements OnApiResult {

    public static final String KEY_CHANNEL_ID = "channel_id";

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    private List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        if( getIntent().getExtras().getInt(KEY_CHANNEL_ID, 0) == 0 ) {
            finish();
        }

        InitRecyclerView();
        DownloadMessages();
    }

    private void InitRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);

        mRecyclerView.setLayoutManager(llm);

        mAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
    private void DownloadMessages() {
        mMessages = new ArrayList<>();

        new GetMessages(this, 1).start();
    }

    @Override
    public void onSuccess(String api, String response) {
        JSONObject json;

        try {
            json        = new JSONObject(response);
            mMessages   = Message.fromJsonArray(json.getJSONArray("messages"));
        } catch (JSONException e) {
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_messages);
            return;
        }
        Utils.Log.info("messages c: "+ mMessages.size());
        mAdapter.setMessages(mMessages);
    }

    @Override
    public void onFail(String api) {
        Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_messages);
    }
}
