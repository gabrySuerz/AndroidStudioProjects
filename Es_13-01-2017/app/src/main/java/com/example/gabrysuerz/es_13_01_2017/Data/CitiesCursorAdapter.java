package com.example.gabrysuerz.es_13_01_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabrysuerz.es_13_01_2017.R;

/**
 * Created by gabrysuerz on 14/01/17.
 */

public class CitiesCursorAdapter extends CursorAdapter {

    public CitiesCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View layout = LayoutInflater.from(context).inflate(R.layout.city, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.cityName = (TextView) layout.findViewById(R.id.txt_city);
        layout.setTag(viewHolder);
        return layout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.cityName.setText(cursor.getString(cursor.getColumnIndex(CitiesHelper.NAME_CITIES)));
    }

    private class ViewHolder {
        TextView cityName;
    }
}
