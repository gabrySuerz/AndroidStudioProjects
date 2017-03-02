package com.gabrysuerz.es_20_02_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {/*
        View view = LayoutInflater.from(context).inflate(R.layout.layout, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.txt1 = (TextView)view.findViewById(R.id.txt1);
        view.setTag(viewHolder);
        return view;*/
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /*ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txt1.setText(cursor.getString(cursor.getColumnIndex(Helper.COLUMN1)));*/
    }

    public class ViewHolder {
        //TextView txt1;
    }
}
