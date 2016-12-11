package hu.adam.sibilum.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import hu.adam.sibilum.ChannelsActivity;
import hu.adam.sibilum.R;

public class CreateChannelDialog extends DialogFragment {

    private ChannelsActivity mCa;

    public CreateChannelDialog() {
        mCa = null;
    }

    public void setChannelsActivity(ChannelsActivity ca) {
        mCa = ca;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder     = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater         = getActivity().getLayoutInflater();
        View view                       = inflater.inflate(R.layout.dialog_create_channel, null);
        final EditText etChannelName    = (EditText)view.findViewById(R.id.etChannelName);

        builder.setTitle(R.string.title_dialog_create_channel)
                .setView(view)
                .setPositiveButton(R.string.btn_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = etChannelName.getText().toString();

                        if( name.isEmpty() )
                            return;

                        if( mCa != null )
                            mCa.createChannel(name);

                        CreateChannelDialog.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateChannelDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
