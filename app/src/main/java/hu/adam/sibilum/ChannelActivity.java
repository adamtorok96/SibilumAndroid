package hu.adam.sibilum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.adapter.MessageAdapter;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.api.GetMessages;
import hu.adam.sibilum.network.api.NewMessage;


public class ChannelActivity extends AppCompatActivity implements OnApiResult, View.OnClickListener, TextView.OnEditorActionListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1000;

    private static final String PREF_IMAGE_COMPRESSION = "image_compression";

    public static final String KEY_CHANNEL_ID       = "channel_id";
    public static final String KEY_CHANNEL_NAME     = "channel_name";

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private ImageButton mBtnSend, mBtnCamera;
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

        mBtnSend = (ImageButton)findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(this);

        mBtnCamera = (ImageButton)findViewById(R.id.btnCamera);
        mBtnCamera.setOnClickListener(this);

        mEtMessage = (EditText)findViewById(R.id.etMessage);
        mEtMessage.setOnEditorActionListener(this);
    }

    private void InitRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        Message msg = new Message(0, mChannelId, App.get().getUser().getId(), Message.TYPE_TEXT, message, null);

        new NewMessage(this, msg).start();
    }

    private void loadMessages(String response) {
        JSONObject json;

        try {
            json        = new JSONObject(response);
            mMessages   = Message.fromJsonArray(json.getJSONArray("messages"));

            runOnUiThread(mUpdateAdapter);
        } catch (JSONException e) {
            Utils.Log.error(e);
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_messages);
        }
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

    private void takePicture() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
    }

    private void scrollToBottom() {
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
        if( view.equals(mBtnSend) )
            sendMessage();
        else if( view.equals(mBtnCamera) )
            takePicture();
    }


    @Override
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
        if( action == EditorInfo.IME_ACTION_SEND ) {
            sendMessage(); return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_IMAGE_CAPTURE ) {

            if( resultCode == RESULT_OK ) {
                Utils.Log.info("picture taken");

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap)extras.get("data");

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int compressionLevel = Integer.parseInt(
                        PreferenceManager
                                .getDefaultSharedPreferences(this)
                                .getString(PREF_IMAGE_COMPRESSION, "0")
                );

                if( !bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, buffer) ) {
                    Utils.snackbar(mRecyclerView, R.string.error_failed_to_send_message);
                    return;
                }

                new NewMessage(ChannelActivity.this,
                        Message.fromBitmap(mChannelId, App.get().getUser().getId(), buffer.toByteArray())
                ).start();
            }

        }
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
}
