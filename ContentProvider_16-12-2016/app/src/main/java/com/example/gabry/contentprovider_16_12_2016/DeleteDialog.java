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

public class DeleteDialog extends DialogFragment {

    private IOnDelete mListener;
    private static final String ID = "id";

    public interface IOnDelete {
        void onYesDelete(long aId);
    }

    public static DeleteDialog getInstance(long aId) {
        DeleteDialog del = new DeleteDialog();
        Bundle vBundle = new Bundle();
        vBundle.putLong(ID,aId);
        del.setArguments(vBundle);
        return del;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        final Long vId = getArguments().getLong(ID);

        build.setTitle("Vuoi davvero cancellare?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onYesDelete(vId);
            }
        }).setNegativeButton("NO", null);
        return build.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (getActivity() instanceof IOnDelete) {
            mListener = (IOnDelete) getActivity();
        }
    }
}
