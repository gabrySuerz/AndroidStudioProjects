package com.esame.suerzgabriele_13_06_2017;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 14/06/17.
 */

public class DeleteDialog extends DialogFragment {


    private IOnDelete mListener;
    private static final String ID = "id";
    private long vID;

    public interface IOnDelete{
        void onDelete(long id);
    }

    public static DeleteDialog getInstance(long aId){
        DeleteDialog del = new DeleteDialog();
        Bundle vBundle = new Bundle();
        vBundle.putLong(ID, aId);
        del.setArguments(vBundle);
        return del;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        vID = getArguments().getLong(ID);

        builder.setTitle("Vuoi eliminare la sessione di allenamento?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDelete(vID);
            }
        }).setNegativeButton("NO", null);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof IOnDelete){
            mListener = (IOnDelete) getActivity();
        }
    }
}
