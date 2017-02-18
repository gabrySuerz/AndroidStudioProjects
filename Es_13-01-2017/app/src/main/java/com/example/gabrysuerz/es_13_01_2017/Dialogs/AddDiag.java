package com.example.gabrysuerz.es_13_01_2017.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by gabrysuerz on 13/01/17.
 */

public class AddDiag extends DialogFragment {

    EditText mEdit;
    private static final String TYPE = "type";

    private IOnAdd mListener = new IOnAdd() {
        @Override
        public void onYesAdd(String city) {

        }
    };

    public interface IOnAdd {
        void onYesAdd(String city);
    }

    public static AddDiag getInstance(String type) {
        AddDiag vDiag = new AddDiag();
        Bundle vBun = new Bundle();
        vBun.putString(TYPE, type);
        vDiag.setArguments(vBun);
        return vDiag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mEdit = new EditText(getActivity());
        builder.setTitle("Scrivi la " + getArguments().getString(TYPE))
                .setView(mEdit)
                .setPositiveButton("INSERISCI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onYesAdd(mEdit.getText().toString());
                    }
                }).setNegativeButton("ANNULLA", null);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnAdd)
            mListener = (IOnAdd) getActivity();
    }
}
