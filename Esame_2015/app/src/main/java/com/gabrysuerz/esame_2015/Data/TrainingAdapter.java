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

public class TrainingAdapter extends CursorAdapter {
    public TrainingAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    private class ViewHolder {
        TextView txt_training_name, txt_date, txt_total_time;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_training, null);
        ViewHolder vVH = new ViewHolder();
        vVH.txt_training_name = (TextView) view.findViewById(R.id.txt_training_name);
        vVH.txt_date = (TextView) view.findViewById(R.id.txt_date);
        vVH.txt_total_time = (TextView) view.findViewById(R.id.txt_total_time);
        view.setTag(vVH);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vVH = (ViewHolder) view.getTag();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        vVH.txt_training_name.setText(cursor.getString(cursor.getColumnIndex(TrainingHelper.TRAINING)));
        vVH.txt_date.setText(sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(TrainingHelper.DATE)))));
        sdf.applyPattern("mm:ss");
        vVH.txt_total_time.setText(sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(TrainingHelper.TIME)) * 1000)));
    }
}
