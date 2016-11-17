package hu.adam.sibilum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.R;
import hu.adam.sibilum.network.Channel;

/**
 * Created by adam on 2016.11.17..
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private List<Channel> mChannels;

    public ChannelAdapter() {
        mChannels = new ArrayList<>();
    }

    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ViewHolder holder, int position) {
        holder.tvChannelName.setText(mChannels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mChannels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChannelName;

        ViewHolder(View view) {
            super(view);

            tvChannelName = (TextView)view.findViewById(R.id.tvChannelName);
        }

    }
}
