package com.esame.suerzgabriele_13_06_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.esame.suerzgabriele_13_06_2017.R;

import java.text.SimpleDateFormat;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class TrainingAdapter extends CursorAdapter {

    public TrainingAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    public class ViewHolder {
        TextView txt_city, txt_date, txt_time;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.cell_training, null);
        ViewHolder vH = new ViewHolder();
        vH.txt_city = (TextView) view.findViewById(R.id.txt_city);
        vH.txt_date = (TextView) view.findViewById(R.id.txt_date);
        vH.txt_time = (TextView) view.findViewById(R.id.txt_time);
        view.setTag(vH);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vH = (ViewHolder) view.getTag();
        vH.txt_city.setText(cursor.getString(cursor.getColumnIndex(TrainingHelper.CITY)));
        vH.txt_date.setText(cursor.getString(cursor.getColumnIndex(TrainingHelper.DATE)));
        vH.txt_time.setText(cursor.getString(cursor.getColumnIndex(TrainingHelper.TIME)));
    }
}
