package com.gabrysuerz.esame_2015.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class DeleteDialog extends DialogFragment {

    private static final String ID = "training_id";
    private IOnDelete mListener;

    public interface IOnDelete{
        void onYes(long aID);
    }

    public static DeleteDialog getInstance(long aID) {
        DeleteDialog vDiag=new DeleteDialog();
        Bundle vBun=new Bundle();
        vBun.putLong(ID, aID);
        vDiag.setArguments(vBun);
        return vDiag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnDelete) {
            mListener = (IOnDelete) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getDialogBuilder().create();
    }

    private AlertDialog.Builder getDialogBuilder() {
        return new AlertDialog.Builder(getActivity()).setTitle("Vuoi eliminare l'allenamento?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onYes(getArguments().getLong(ID));
            }
        }).setNegativeButton("NO", null);
    }
}
