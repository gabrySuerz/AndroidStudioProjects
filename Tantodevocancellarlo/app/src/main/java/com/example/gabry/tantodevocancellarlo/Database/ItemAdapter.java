package com.example.gabry.tantodevocancellarlo.Database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.gabry.tantodevocancellarlo.R;

import java.util.ArrayList;

/**
 * Created by gabry on 13/12/2016.
 */

public class ItemAdapter extends BaseAdapter {

    public static final String BUNID = "idToBundle";
    public static final String BUNFIRST = "firstToBundle";
    public static final String BUNSECOND = "secondToBundle";

    ArrayList<Item> data;
    Context context;

    public ItemAdapter(Context aContext, ArrayList<Item> aData) {
        context = aContext;
        data = aData;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Item getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    public class ViewHolder {
        TextView first, second;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.first = (TextView) convertView.findViewById(R.id.firstTxt);
            viewHolder.second = (TextView) convertView.findViewById(R.id.secondTxt);
            convertView.setTag(viewHolder);
        }
        final Item vItem = getItem(position);
        ViewHolder vVH = (ViewHolder) convertView.getTag();

        vVH.first.setText("" + vItem.first);
        vVH.second.setText("" + vItem.second);

        Button btnModify = (Button) convertView.findViewById(R.id.actionBtn);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle vBun = new Bundle();
                vBun.putInt(BUNID, position);
                vBun.putString(BUNFIRST, "" + vItem.first);
                vBun.putString(BUNSECOND, "" + vItem.second);
                Intent vIntent = new Intent();
                vIntent.putExtras(vBun);
                context.startActivity(vIntent);
            }
        });

        return convertView;
    }
}
