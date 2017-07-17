package com.gabrysuerz.suerzgabriele.Data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.R;

import java.util.ArrayList;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class FoodsListAdapter extends BaseAdapter {

    ArrayList<Food> mFoods;
    Context mContext;

    public FoodsListAdapter(Context aContext, ArrayList<Food> aData) {
        mFoods = aData;
        mContext = aContext;
    }

    @Override
    public int getCount() {
        return mFoods.size();
    }

    @Override
    public Food getItem(int position) {
        return mFoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFoods.get(position).getmID();
    }

    public class ViewHolder {
        TextView txt_food_name, txt_food_quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vCell;
        if (convertView == null) {
            vCell = LayoutInflater.from(mContext).inflate(R.layout.cell_food, null);

            ViewHolder vHolder = new ViewHolder();
            vHolder.txt_food_name = (TextView) vCell.findViewById(R.id.txt_food_name);
            vHolder.txt_food_quantity = (TextView) vCell.findViewById(R.id.txt_food_quantity);

            vCell.setTag(vHolder);
        } else {
            vCell = convertView;
        }

        Food vItem = getItem(position);
        ViewHolder vVH = (ViewHolder) vCell.getTag();

        vVH.txt_food_name.setText("" + vItem.getmFoodName());
        vVH.txt_food_quantity.setText("" + vItem.getmQuantity());
        return vCell;
    }
}
