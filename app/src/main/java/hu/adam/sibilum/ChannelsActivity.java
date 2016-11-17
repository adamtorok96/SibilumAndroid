package hu.adam.sibilum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hu.adam.sibilum.adapter.ChannelAdapter;

public class ChannelsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChannelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChannelAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
