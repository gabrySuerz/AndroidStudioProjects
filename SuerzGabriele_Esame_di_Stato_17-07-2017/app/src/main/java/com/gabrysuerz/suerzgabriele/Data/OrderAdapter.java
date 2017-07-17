package com.gabrysuerz.suerzgabriele.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class OrderAdapter extends CursorAdapter {
    public OrderAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    public class ViewHolder {
        TextView txt_date_order_list, txt_quantity_order_list, txt_import_order_list;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View vView = LayoutInflater.from(context).inflate(R.layout.cell_order, null);
        ViewHolder vVH = new ViewHolder();
        vVH.txt_date_order_list = (TextView) vView.findViewById(R.id.txt_date_order_list);
        vVH.txt_quantity_order_list = (TextView) vView.findViewById(R.id.txt_quantity_order_list);
        vVH.txt_import_order_list = (TextView) vView.findViewById(R.id.txt_import_order_list);
        vView.setTag(vVH);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vVH = (ViewHolder) view.getTag();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        vVH.txt_date_order_list.setText(sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(OrderHelper.DATE)))));
        vVH.txt_quantity_order_list.setText("" + cursor.getInt(cursor.getColumnIndex(OrderHelper.QUANTITY)));
        vVH.txt_import_order_list.setText("€ " + cursor.getLong(cursor.getColumnIndex(OrderHelper.PRICE)));
    }
}
