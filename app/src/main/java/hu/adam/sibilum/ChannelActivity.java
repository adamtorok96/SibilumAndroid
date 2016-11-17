package hu.adam.sibilum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hu.adam.sibilum.adapter.MessageAdapter;


public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvChannelList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);

        mRecyclerView.setLayoutManager(llm);

        mAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
