package com.gabrysuerz.es_20_02_2017;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class MyDialog extends DialogFragment {

    //private static final String MYSTRING = "MYSTRING";

    public interface IOnListen {
        void onYes();

        void onNo();
    }

    private IOnListen mListener = new IOnListen() {

        @Override
        public void onYes() {

        }

        @Override
        public void onNo() {

        }
    };

    public MyDialog getInstance() {
        MyDialog vDiag = new MyDialog();
        /*Bundle vBun = new Bundle();
        vBun.putString(MYSTRING, "");
        vDiag.setArguments(vBun);*/
        return vDiag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getBuilder().create();
    }

    public AlertDialog.Builder getBuilder() {
        //mEdit = new EditText(getActivity());
        return new AlertDialog.Builder(getActivity()).setTitle("Scrivi la "/* + getArguments().getString(MYSTRING)*/)
                //.setView(mEdit)
                .setPositiveButton("INSERISCI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mListener.onYesAdd(mEdit.getText().toString());
                    }
                }).setNegativeButton("ANNULLA", null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mListener instanceof IOnListen) {
            mListener = (IOnListen) getActivity();
        }
    }
}
