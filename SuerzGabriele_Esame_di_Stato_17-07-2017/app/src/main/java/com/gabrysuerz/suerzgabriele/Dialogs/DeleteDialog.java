package com.gabrysuerz.suerzgabriele.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class DeleteDialog extends DialogFragment {

    public static final String TITLE = "alert_title";
    public static final String ID = "id_delete";

    private IOnConfirm mListener;

    public interface IOnConfirm {
        void onYes(long aID);
    }

    public static DeleteDialog getInstance(String aTitle, long aID) {
        DeleteDialog vDiag = new DeleteDialog();
        Bundle vBun = new Bundle();
        vBun.putString(TITLE, aTitle);
        vBun.putLong(ID, aID);
        vDiag.setArguments(vBun);
        return vDiag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnConfirm) {
            mListener = (IOnConfirm) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
        vBuilder.setTitle("Vuoi cancellare l'ordine " + getArguments().getString(TITLE) + "?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onYes(getArguments().getLong(ID));
            }
        }).setNegativeButton("NO", null);
        return vBuilder.create();
    }
}
