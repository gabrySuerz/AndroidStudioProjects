package com.esame.suerzgabriele.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class DeleteDialog extends DialogFragment {

    private static final String MY_ID = "idPrenotazione";

    public interface IOnDelete {
        void onYes(long aId);
    }

    private IOnDelete mListener;

    public static DeleteDialog getInstance(long id) {
        DeleteDialog vDiag = new DeleteDialog();
        Bundle vBun = new Bundle();
        vBun.putLong(MY_ID, id);
        vDiag.setArguments(vBun);
        return vDiag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getBuilder().create();
    }

    public AlertDialog.Builder getBuilder() {
        return new AlertDialog.Builder(getActivity()).setTitle("Sicuro di voler eliminare la prenotazione?")
                .setPositiveButton("ELIMINARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onYes(getArguments().getLong(MY_ID));
                    }
                }).setNegativeButton("ANNULLA", null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnDelete) {
            mListener = (IOnDelete) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
