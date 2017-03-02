package com.esame.suerzgabriele.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.esame.suerzgabriele.R;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class ReservationCursorAdapter extends CursorAdapter {

    public ReservationCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.txtName = (TextView)view.findViewById(R.id.txt_name);
        viewHolder.txtPhone = (TextView)view.findViewById(R.id.txt_phone);
        viewHolder.txtPerson = (TextView)view.findViewById(R.id.txt_nPerson);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txtName.setText(cursor.getString(cursor.getColumnIndex(ReservationHelper.NAME)));
        viewHolder.txtPhone.setText(cursor.getString(cursor.getColumnIndex(ReservationHelper.PHONE)));
        viewHolder.txtPerson.setText(cursor.getString(cursor.getColumnIndex(ReservationHelper.PERSON)));
    }

    public class ViewHolder {
        TextView txtName, txtPhone, txtPerson;
    }
}
