package com.gabrysuerz.firebasenotes_05_07_2017.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 03/07/17.
 */

public class DialogDelete extends DialogFragment {

    public interface IOnDelete {
        void onYes(String id);
    }

    private static final String ID = "NoteID";
    private IOnDelete mListener;

    public static DialogDelete getInstance(String id){
        DialogDelete diag = new DialogDelete();
        Bundle bun = new Bundle();
        bun.putString(ID, id);
        diag.setArguments(bun);
        return diag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Vuoi cancellare?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onYes(getArguments().getString(ID));
            }
        }).setNegativeButton("NO", null);
        return dialog.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof IOnDelete) {
            mListener = (IOnDelete) activity;
        }
    }
}
