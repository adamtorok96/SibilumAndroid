package hu.adam.sibilum.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.ChannelActivity;
import hu.adam.sibilum.R;
import hu.adam.sibilum.models.Channel;

/**
 * Created by adam on 2016.11.17..
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private List<Channel> mChanels;

    public ChannelAdapter() {
        mChanels = new ArrayList<>();
    }

    public void setChannels(List<Channel> chanels) {
        mChanels = chanels;
    }

    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ViewHolder holder, int position) {
        holder.mChannel = mChanels.get(position);
        holder.tvChannelName.setText(mChanels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mChanels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private Channel mChannel;
        private TextView tvChannelName;

        ViewHolder(View view) {
            super(view);

            tvChannelName = (TextView)view.findViewById(R.id.tvChannelName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Channel.class);
                    intent.putExtra(ChannelActivity.KEY_CHANNEL_ID, mChannel.getId());

                    view.getContext().startActivity(intent);
                }
            });
        }

    }
}
