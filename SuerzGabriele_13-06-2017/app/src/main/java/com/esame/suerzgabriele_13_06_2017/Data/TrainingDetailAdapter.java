package com.esame.suerzgabriele_13_06_2017.Data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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

public class TrainingDetailAdapter extends CursorAdapter {

    public TrainingDetailAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    public class ViewHolder {
        TextView txt_laps, txt_lap_time;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_detail, null);
        ViewHolder vH = new ViewHolder();
        vH.txt_laps = (TextView) view.findViewById(R.id.txt_n_laps);
        vH.txt_lap_time = (TextView) view.findViewById(R.id.txt_time_lap);
        view.setTag(vH);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vH = (ViewHolder) view.getTag();
        vH.txt_laps.setText("Giro " + cursor.getLong(cursor.getColumnIndex(TrainingDetailHelper.N_LAPS)));
        vH.txt_lap_time.setText(cursor.getString(cursor.getColumnIndex(TrainingDetailHelper.TIME)));
    }

}
