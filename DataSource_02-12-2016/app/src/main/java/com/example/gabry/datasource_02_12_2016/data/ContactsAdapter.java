package com.example.gabry.datasource_02_12_2016.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gabry.datasource_02_12_2016.data.Contact;
import com.example.gabry.datasource_02_12_2016.R;

import java.util.ArrayList;

/**
 * Created by gabry on 02/12/2016.
 */

public class ContactsAdapter extends BaseAdapter {

    ArrayList<Contact> mData;
    Context mContext;

    public ContactsAdapter(Context aContext, ArrayList<Contact> aData) {
        mData = aData;
        mContext = aContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Contact getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).mID;
    }

    public class ViewHolder {
        TextView mID, mName, mSurname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, null);

            ViewHolder vHolder = new ViewHolder();
            vHolder.mID = (TextView) convertView.findViewById(R.id.txtId);
            vHolder.mName = (TextView) convertView.findViewById(R.id.txtName);
            vHolder.mSurname = (TextView) convertView.findViewById(R.id.txtSurname);

            convertView.setTag(vHolder);
        }
        Contact vItem = getItem(position);
        ViewHolder vVH = (ViewHolder) convertView.getTag();

        vVH.mID.setText("" + vItem.mID);
        vVH.mName.setText(" " + vItem.mName);
        vVH.mSurname.setText(" " + vItem.mSurname);
        return convertView;
    }
}
