package com.gabrysuerz.esame_2015.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gabrysuerz.esame_2015.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class LapAdapter extends CursorAdapter {
    public LapAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    private class ViewHolder {
        TextView txt_lap_number, txt_lap_time;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_lap, null);
        ViewHolder vVH = new ViewHolder();
        vVH.txt_lap_number = (TextView) view.findViewById(R.id.txt_lap_number);
        vVH.txt_lap_time = (TextView) view.findViewById(R.id.txt_lap_time);
        view.setTag(vVH);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vVH = (ViewHolder) view.getTag();
        vVH.txt_lap_number.setText("Giro " + cursor.getInt(cursor.getColumnIndex(LapHelper.NUMBER)));
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        vVH.txt_lap_time.setText(sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(LapHelper.TIME)) * 1000)));
    }
}
