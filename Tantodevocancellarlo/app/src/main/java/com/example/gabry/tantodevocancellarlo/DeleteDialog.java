package com.example.gabry.tantodevocancellarlo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabry on 13/12/2016.
 */

public class DeleteDialog extends DialogFragment {
    public interface IOnDelete{
        void onYes();
    }

    public static DeleteDialog getInstance() {
        return new DeleteDialog();
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
        if (getFragmentManager() instanceof IOnDelete) {
            mListener = (IOnDelete) getFragmentManager();
        }
    }
}
