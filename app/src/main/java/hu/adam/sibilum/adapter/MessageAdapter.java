package hu.adam.sibilum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.adam.sibilum.R;
import hu.adam.sibilum.network.Message;

/**
 * Created by adam on 2016.11.17..
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter() {
        mMessages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMessage.setText(mMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessage;

        ViewHolder(View view) {
            super(view);

            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        }

    }


}
