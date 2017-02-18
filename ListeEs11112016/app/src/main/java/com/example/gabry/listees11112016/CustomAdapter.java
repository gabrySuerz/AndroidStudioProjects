package com.example.gabry.listees11112016;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gabry on 11/11/2016.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Item> mData;
    private Context mContext;

    public CustomAdapter(Context aContext, ArrayList<Item> aData) {
        mData = aData;
        mContext = aContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Item getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).mId;
    }

    class ViewHolder{
        TextView mName;
        TextView mValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vCell;
        if (convertView == null) {
            LayoutInflater vInflater = LayoutInflater.from(mContext);
            vCell = vInflater.inflate(R.layout.item_layout, null);

            TextView vName = (TextView) vCell.findViewById(R.id.txtName);
            TextView vValue = (TextView) vCell.findViewById(R.id.txtValue);

            ViewHolder vHolder = new ViewHolder();
            vHolder.mName = vName;
            vHolder.mValue = vValue;

            vCell.setTag(vHolder);
        } else {
            vCell = convertView;
        }

        Item vItem = getItem(position);

        ViewHolder viewHolder = (ViewHolder) vCell.getTag();

        viewHolder.mName.setText(vItem.mName);
        viewHolder.mValue.setText("" + vItem.mLiter);
        return vCell;
    }
}
