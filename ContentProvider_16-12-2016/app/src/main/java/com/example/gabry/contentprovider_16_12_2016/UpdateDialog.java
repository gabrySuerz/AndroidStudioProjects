package com.example.gabry.contentprovider_16_12_2016;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 23/12/16.
 */

public class UpdateDialog extends DialogFragment {

    public IOnUpdate mListener;
    private static final String ID = "id";

    public interface IOnUpdate {
        void onYesUpdate(long vId);
    }

    public static UpdateDialog getInstance(long aId) {
        UpdateDialog vUpdate = new UpdateDialog();
        Bundle vBundle = new Bundle();
        vBundle.putLong(ID, aId);
        vUpdate.setArguments(vBundle);
        return vUpdate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final long vId = getArguments().getLong(ID);

        builder.setTitle("Vuoi modificare l'elemento?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onYesUpdate(vId);
            }
        }).setNegativeButton("NO", null);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnUpdate) {
            mListener = (IOnUpdate) getActivity();
        }
    }
}
