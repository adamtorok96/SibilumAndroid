package hu.adam.sibilum;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.adapter.ChannelAdapter;
import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.Channel;
import hu.adam.sibilum.network.api.GetChannels;
import hu.adam.sibilum.network.api.GetUsers;

public class ChannelsActivity extends AppCompatActivity implements OnApiResult {

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
        // TODO: create channel mFab.setOnClickListener();
    }

    private void DownloadChannels() {
        mChannels = new ArrayList<>();

        new GetChannels(this).start();
    }

    @Override
    public void onSuccess(String api, String response) {
        JSONObject json;

        try {
            json        = new JSONObject(response);
            mChannels   = Channel.fromJsonArray(json.getJSONArray("channels"));
        } catch (JSONException e) {
            Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_channels);
            return;
        }

        mAdapter.setChannels(mChannels); // refresh list?
    }

    @Override
    public void onFail(String api) {
        Utils.snackbar(mRecyclerView, R.string.error_failed_to_download_channels);
    }
}
