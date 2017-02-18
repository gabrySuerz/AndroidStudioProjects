package com.example.gabrysuerz.es_13_01_2017.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by gabrysuerz on 19/01/17.
 */

public class UpdateTempDiag extends DialogFragment {

    EditText mEdit;
    static final String ID = "id";

    private IOnUpdateTemp mListener = new IOnUpdateTemp() {
        @Override
        public void onYesUpdate(int temp, long id) {

        }
    };

    public interface IOnUpdateTemp {
        void onYesUpdate(int temp, long id);
    }

    public static UpdateTempDiag getInstance(long aId) {
        UpdateTempDiag vDiag = new UpdateTempDiag();
        Bundle vBundle = new Bundle();
        vBundle.putLong(ID, aId);
        vDiag.setArguments(vBundle);
        return vDiag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mEdit = new EditText(getActivity());
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = setUp();

        return builder.create();
    }

    private AlertDialog.Builder setUp() {
        return new AlertDialog.Builder(getActivity()).setTitle("Scrivi la temperatura della città").setView(mEdit).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!mEdit.getText().toString().equals(""))
                    mListener.onYesUpdate(Integer.parseInt(mEdit.getText().toString()),getArguments().getLong(ID));
                else setUp();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnUpdateTemp) {
            mListener = (IOnUpdateTemp) getActivity();
        }
    }
}
