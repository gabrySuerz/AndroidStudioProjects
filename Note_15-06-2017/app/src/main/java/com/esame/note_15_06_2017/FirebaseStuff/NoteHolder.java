package com.esame.note_15_06_2017.FirebaseStuff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.esame.note_15_06_2017.Models.Note;
import com.esame.note_15_06_2017.R;

import java.text.SimpleDateFormat;

/**
 * Created by gabrysuerz on 03/07/17.
 */

public class NoteHolder extends RecyclerView.ViewHolder {

    private final TextView mTitleField;
    private final TextView mDateField;
    private ImageView mImageView;

    private Note note;

    private ClickListener mClickListener;

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    //Interface to send callbacks...
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public NoteHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.image_view_note);
        mTitleField = (TextView) itemView.findViewById(R.id.note_title);
        mDateField = (TextView) itemView.findViewById(R.id.note_creation_date);

        mDrawableBuilder = TextDrawable.builder()
                .round();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }

    public void setNote(Note note) {
        this.note = note;
        setGUI();
    }

    public Note getNote() {
        return note;
    }

    private void setGUI() {
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(note.getTitle().charAt(0)), mColorGenerator.getColor(note.getTitle()));
        mImageView.setImageDrawable(drawable);
        mTitleField.setText(note.getTitle());
        String formatTime = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        mDateField.setText(sdf.format(note.getDateCreated()));
    }
}