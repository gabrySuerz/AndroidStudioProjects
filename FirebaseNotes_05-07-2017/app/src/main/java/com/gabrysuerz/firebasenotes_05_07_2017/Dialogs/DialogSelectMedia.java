package com.gabrysuerz.firebasenotes_05_07_2017.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gabrysuerz.firebasenotes_05_07_2017.R;

/**
 * Created by gabrysuerz on 05/07/17.
 */

public class DialogSelectMedia extends DialogFragment {

    private IOnMedia mListener;

    public interface IOnMedia {
        void onImage();
        void onAudio();
        void onSketch();
    }

    public static DialogSelectMedia getInstance() {
        return new DialogSelectMedia();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_media, null);
        view.findViewById(R.id.btn_image_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onImage();
                dismiss();
            }
        });
        view.findViewById(R.id.btn_audio_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAudio();
                dismiss();
            }
        });
        view.findViewById(R.id.btn_sketch_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSketch();
                dismiss();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scegli cosa allegare").setView(view).setNegativeButton("ANNULLA", null);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IOnMedia) {
            mListener = (IOnMedia) activity;
        }
    }
}
