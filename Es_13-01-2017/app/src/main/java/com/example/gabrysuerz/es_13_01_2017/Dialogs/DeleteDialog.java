package com.example.gabrysuerz.es_13_01_2017.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 23/12/16.
 */

public class DeleteDialog extends DialogFragment {

    private IOnDelete mListener;
    private long mId;
    private static final String ID = "id";
    private static final String TYPE = "type";

    public interface IOnDelete {
        void onYesDelete(long aId);
    }

    public static DeleteDialog getInstance(long aId, String type) {
        DeleteDialog del = new DeleteDialog();
        Bundle vBundle = new Bundle();
        vBundle.putLong(ID, aId);
        vBundle.putString(TYPE, type);
        del.setArguments(vBundle);
        return del;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        mId = getArguments().getLong(ID);

        build.setTitle("Vuoi davvero eliminare la " + getArguments().getString(TYPE) + "?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onYesDelete(mId);
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
