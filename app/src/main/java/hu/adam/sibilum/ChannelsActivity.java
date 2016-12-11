package hu.adam.sibilum;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.adapter.ChannelAdapter;
import hu.adam.sibilum.dialogs.CreateChannelDialog;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Channel;
import hu.adam.sibilum.network.Api;
import hu.adam.sibilum.network.api.GetChannels;
import hu.adam.sibilum.network.api.GetUsers;
import hu.adam.sibilum.network.api.NewChannel;

public class ChannelsActivity extends AppCompatActivity implements OnApiResult, View.OnClickListener {

    private static final String TAG_CREATE_CHANNEL = "dialog_create_channel";

    private RecyclerView mRecyclerView;
    private ChannelAdapter mAdapter;
    private FloatingActionButton mFab;

    private List<Channel> mChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        InitRecyclerView();
        App.get().DownloadUsers();
        DownloadChannels();
    }

    private void InitRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChannelAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mFab = (FloatingActionButton)findViewById(R.id.fab_channels);
        mFab.setOnClickListener(this);
    }

    private void DownloadChannels() {
        mChannels = new ArrayList<>();

        new GetChannels(this).start();
    }

    private void InitNewChannel(String response) {
        Channel channel = null;

        try {
            channel = Channel.fromString(response);
        } catch (JSONException ignored) {

        }

        if( channel != null ) {
            mChannels.add(channel);

            runOnUiThread(mNotfiyAdapter);
            Utils.snackbar(mRecyclerView, R.string.channel_create_successfully);
        } else
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_create_channel);
    }

    private void InitChannels(String response) {
        JSONObject json;

        try {
            json        = new JSONObject(response);
            mChannels   = Channel.fromJsonArray(json.getJSONArray("channels"));

            runOnUiThread(mUpdateAdapter);
        } catch (JSONException e) {
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_channels);
        }
    }

    public void createChannel(String name) {
        new NewChannel(this, name).start();
    }

    @Override
    public void onSuccess(String api, String response) {
        if( api.equals(Api.API_CHANNEL_LIST) )
            InitChannels(response);
        else if( api.equals(Api.API_CHANNEL_NEW) )
            InitNewChannel(response);
    }

    @Override
    public void onFail(String api) {
        if( api.equals(Api.API_CHANNEL_LIST) )
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_channels);
        else if( api.equals(Api.API_CHANNEL_NEW) )
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_create_channel);
    }

    @Override
    public void onClick(View view) {
        CreateChannelDialog ccd = new CreateChannelDialog();
        ccd.setChannelsActivity(this);
        ccd.show(getSupportFragmentManager(), TAG_CREATE_CHANNEL);
    }

    public Runnable mUpdateAdapter = new Runnable() {
        @Override
        public void run() {
            mAdapter.setChannels(mChannels);
            mAdapter.notifyDataSetChanged();
        }
    };

    public Runnable mNotfiyAdapter = new Runnable() {
        @Override
        public void run() {
            mAdapter.notifyDataSetChanged();
        }
    };
}
