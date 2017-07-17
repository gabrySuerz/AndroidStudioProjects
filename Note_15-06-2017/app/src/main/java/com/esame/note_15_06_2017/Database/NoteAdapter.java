package com.esame.note_15_06_2017.Database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.esame.note_15_06_2017.R;

import java.text.SimpleDateFormat;

/**
 * Created by gabrysuerz on 22/06/17.
 */

public class NoteAdapter extends CursorAdapter {

    public NoteAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View layout = LayoutInflater.from(context).inflate(R.layout.content_home, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView) layout.findViewById(R.id.note_title);
        viewHolder.date = (TextView) layout.findViewById(R.id.note_creation_date);
        layout.setTag(viewHolder);
        return layout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String formatTime = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(NoteHelper.TITLE)));
        viewHolder.title.setText(sdf.format(cursor.getLong(cursor.getColumnIndex(NoteHelper.DATE))));
    }

    private class ViewHolder {
        TextView title, date;
    }
}
