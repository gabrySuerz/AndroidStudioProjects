package com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gabrysuerz.anagrafiche_es_19_01_2017.R;

/**
 * Created by gabrysuerz on 19/01/17.
 */
public class PeopleCursorAdapter extends CursorAdapter {


    public PeopleCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View customView = LayoutInflater.from(context).inflate(R.layout.cell, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.txt_surname = (TextView) customView.findViewById(R.id.txt_surname);
        viewHolder.txt_name = (TextView) customView.findViewById(R.id.txt_name);
        //viewHolder.btn_image = (ImageButton) customView.findViewById(R.id.btn_image);
        customView.setTag(viewHolder);
        return customView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txt_surname.setText(cursor.getString(cursor.getColumnIndex(PersonHelper.SURNAME)));
        viewHolder.txt_name.setText(cursor.getString(cursor.getColumnIndex(PersonHelper.NAME)));
    }


    private class ViewHolder {
        TextView txt_name, txt_surname;
        ImageButton btn_image;
    }
}
