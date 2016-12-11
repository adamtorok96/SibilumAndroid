package hu.adam.sibilum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.adapter.MessageAdapter;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.api.GetMessages;
import hu.adam.sibilum.network.api.NewMessage;


public class ChannelActivity extends AppCompatActivity implements OnApiResult, View.OnClickListener, TextView.OnEditorActionListener {

    public static final String KEY_CHANNEL_ID       = "channel_id";
    public static final String KEY_CHANNEL_NAME     = "channel_name";

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private Button mBtnSend;
    private EditText mEtMessage;

    private List<Message> mMessages;

    private int mChannelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        InitActivity();
        InitRecyclerView();

        downloadMessages();
    }

    private void InitActivity() {
        Bundle extras = getIntent().getExtras();

        mChannelId = extras.getInt(KEY_CHANNEL_ID, 0);

        if( mChannelId == 0 ) {
            finish();
        }

        setTitle(extras.getString(KEY_CHANNEL_NAME, "Unknown channel"));

        mBtnSend = (Button)findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(this);

        mEtMessage = (EditText)findViewById(R.id.etMessage);
        mEtMessage.setOnEditorActionListener(this);
    }

    private void InitRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setReverseLayout(true);

        mRecyclerView.setLayoutManager(llm);

        mAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void downloadMessages() {
        mMessages = new ArrayList<>();

        new GetMessages(this, mChannelId).start();
    }

    private void sendMessage() {
        String message = mEtMessage.getText().toString();

        if( message.isEmpty() )
            return;

        new NewMessage(this, mChannelId, App.get().getUser().getId(), message).start();
    }

    private void loadMessages(String response) {
        JSONObject json;

        try {
            json        = new JSONObject(response);
            mMessages   = Message.fromJsonArray(json.getJSONArray("messages"));
        } catch (JSONException e) {
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_messages);
            return;
        }

        runOnUiThread(mUpdateAdapter);
    }

    private void loadMessage(String response) {
        Message message = null;

        try {
            message = Message.fromString(response);
        } catch (JSONException ignored) {
        }

        if( message != null ) {
            mMessages.add(message);

            runOnUiThread(mNotifyAdapter);
            runOnUiThread(mClearMessageText);
        } else
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_send_message);
    }

    private void scrollToBottom() {
        //mRecyclerView.scrollToPosition(mMessages.size());
        mRecyclerView.smoothScrollToPosition(mMessages.size());
    }

    @Override
    public void onSuccess(String api, String response) {
        if( api.equals(Api.API_MESSAGE_LIST) )
            loadMessages(response);
        else if( api.equals(Api.API_MESSAGE_NEW) )
            loadMessage(response);
    }

    @Override
    public void onFail(String api) {
        if( api.equals(Api.API_MESSAGE_LIST) )
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_messages);
        else if( api.equals(Api.API_MESSAGE_NEW) )
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_send_message);
    }

    @Override
    public void onClick(View view) {
        sendMessage();
    }

    public Runnable mUpdateAdapter = new Runnable() {

        @Override
        public void run() {
            mAdapter.setMessages(mMessages);
            mAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    };

    public Runnable mNotifyAdapter = new Runnable() {
        @Override
        public void run() {
            mAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    };

    public Runnable mClearMessageText = new Runnable() {
        @Override
        public void run() {
            mEtMessage.getText().clear();
        }
    };

    @Override
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {

        if( action == EditorInfo.IME_ACTION_SEND ) {
            sendMessage();

            return true;
        }

        return false;
    }
}
