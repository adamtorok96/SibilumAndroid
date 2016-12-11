package hu.adam.sibilum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.App;
import hu.adam.sibilum.R;
import hu.adam.sibilum.Utils;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.models.User;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter() {
        mMessages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        mMessages   = messages;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int gravity = mMessages.get(position).getUser() == App.get().getUser().getId() ? Gravity.END : Gravity.START;
        int user    = mMessages.get(position).getUser();

        if( isNewAuthor(position) ) {
            User author = App.get().getUserById(user);

            Utils.Log.info("new: "+ mMessages.get(position).getUser() +" prev:" + mMessages.get(position).getUser());

            holder.tvUser.setText(author == null ? "Unknown user" : author.getName());
            holder.tvUser.setGravity(gravity);
            holder.tvUser.setVisibility(View.VISIBLE);
        }

        holder.tvMessage.setText(mMessages.get(position).getMessage());
        holder.tvMessage.setGravity(gravity);
    }

    private boolean isNewAuthor(int position) {
        return position == 0 || mMessages.get(position).getUser() != mMessages.get(position - 1).getUser();
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUser;
        private TextView tvMessage;

        ViewHolder(View view) {
            super(view);

            tvUser      = (TextView)view.findViewById(R.id.tvUser);
            tvMessage   = (TextView)view.findViewById(R.id.tvMessage);
        }

    }
}
