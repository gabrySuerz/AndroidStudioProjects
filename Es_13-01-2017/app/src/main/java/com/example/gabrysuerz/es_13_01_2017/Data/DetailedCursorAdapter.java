package com.example.gabrysuerz.es_13_01_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabrysuerz.es_13_01_2017.Data.TempHelper;
import com.example.gabrysuerz.es_13_01_2017.R;

/**
 * Created by gabrysuerz on 20/01/17.
 */

public class DetailedCursorAdapter extends CursorAdapter {
    public DetailedCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View layout = LayoutInflater.from(context).inflate(R.layout.temp, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.city_temp = (TextView) layout.findViewById(R.id.txt_temp);
        viewHolder.city_date = (TextView) layout.findViewById(R.id.txt_date);
        layout.setTag(viewHolder);
        return layout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.city_temp.setText("" + cursor.getInt(cursor.getColumnIndex(TempHelper.TEMP_CITIES)));
        viewHolder.city_date.setText(cursor.getString(cursor.getColumnIndex(TempHelper.TEMP_DATE)));
    }

    private class ViewHolder {
        TextView city_temp, city_date;
    }
}
