package com.gabrysuerz.suerzgabriele.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.R;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class FoodAdapter extends CursorAdapter {
    public FoodAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    public Food food;

    public class ViewHolder {
        TextView txt_food_name, txt_food_quantity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View vView = LayoutInflater.from(context).inflate(R.layout.cell_food, null);
        ViewHolder vVH = new ViewHolder();
        vVH.txt_food_name = (TextView) vView.findViewById(R.id.txt_food_name);
        vVH.txt_food_quantity = (TextView) vView.findViewById(R.id.txt_food_quantity);
        vView.setTag(vVH);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vVH = (ViewHolder) view.getTag();
        food = new Food(0, cursor.getString(cursor.getColumnIndex(FoodHelper.NAME_FOOD)), cursor.getInt(cursor.getColumnIndex(FoodHelper.QUANTITY_FOOD)), cursor.getInt(cursor.getColumnIndex(FoodHelper.PRICE_FOOD)));
        vVH.txt_food_name.setText(food.getmFoodName());
        vVH.txt_food_quantity.setText("" + food.getmQuantity());
    }
}
