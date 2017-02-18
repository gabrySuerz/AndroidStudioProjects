package com.example.gabry.contentprovider_16_12_2016.Data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabry.contentprovider_16_12_2016.R;

/**
 * Created by gabrysuerz on 23/12/16.
 */

public class ContactCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;

    public ContactCursorAdapter(Context aContext, Cursor aCursor) {
        super(aContext, aCursor, false);
    }

    class ViewHolder {
        TextView txt_id, txt_name, txt_surname;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);

        ViewHolder vHolder = new ViewHolder();
        vHolder.txt_id = (TextView) view.findViewById(R.id.txtId);
        vHolder.txt_name = (TextView) view.findViewById(R.id.txtName);
        vHolder.txt_surname = (TextView) view.findViewById(R.id.txtSurname);
        view.setTag(vHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txt_id.setText("" + cursor.getLong(cursor.getColumnIndex(ContactsHelper._ID)));
        viewHolder.txt_name.setText("" + cursor.getString(cursor.getColumnIndex(ContactsHelper.NAME)));
        viewHolder.txt_surname.setText("" + cursor.getString(cursor.getColumnIndex(ContactsHelper.SURNAME)));
    }
}
