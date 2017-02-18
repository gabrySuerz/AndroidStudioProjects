package com.example.gabry.datasource_02_12_2016;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by gabry on 02/12/2016.
 */
public class Dialog extends DialogFragment {

    public interface IOnDelete{
        void onYes();
    }

    public static Dialog getInstance() {
        return new Dialog();
    }

    private IOnDelete mListener = new IOnDelete() {
        @Override
        public void onYes() {

        }
    };

    @Override
    public android.app.Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        //Bundle vBundle = getArguments();

        build.setTitle("Vuoi davvero cancellare?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onYes();
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
