package com.gabrysuerz.suerzgabriele.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.Data.Food;
import com.gabrysuerz.suerzgabriele.Data.FoodAdapter;
import com.gabrysuerz.suerzgabriele.Data.FoodHelper;
import com.gabrysuerz.suerzgabriele.Data.FoodsListAdapter;
import com.gabrysuerz.suerzgabriele.Data.OrderContentProvider;
import com.gabrysuerz.suerzgabriele.Data.OrderHelper;
import com.gabrysuerz.suerzgabriele.Dialogs.ClearDialog;
import com.gabrysuerz.suerzgabriele.R;

import java.util.ArrayList;

public class ORD02 extends AppCompatActivity implements ClearDialog.IOnConfirm {

    public final static String ID = "foreign_key";
    private static final String CLEAR_TAG = "clear";
    private static final String FOODS = "modified_foods";

    private ListView list_ordered_foods;
    private TextView txt_price;

    private FoodsListAdapter mAdapter;
    private ArrayList<Food> mArrayFoods;
    private long mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord02);

        mID = getIntent().getExtras().getLong(ID);
        txt_price = (TextView) findViewById(R.id.txt_price);

        if (savedInstanceState != null) {
            mArrayFoods = (ArrayList<Food>) savedInstanceState.getSerializable(FOODS);
        } else {
            initList();
        }

        mAdapter = new FoodsListAdapter(this, mArrayFoods);

        list_ordered_foods = (ListView) findViewById(R.id.list_ordered_foods);
        list_ordered_foods.setAdapter(mAdapter);

        list_ordered_foods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food vFood = (Food) adapterView.getItemAtPosition(i);
                vFood.setmQuantity(vFood.getmQuantity() + 1);
                mAdapter.notifyDataSetChanged();
            }
        });
        list_ordered_foods.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClearDialog.getInstance(((TextView) view.findViewById(R.id.txt_food_name)).getText().toString(), i).show(getSupportFragmentManager(), CLEAR_TAG);
                return true;
            }
        });

        Button btn_confirm_modify = (Button) findViewById(R.id.btn_confirm_modify);
        btn_confirm_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyOrder();
            }
        });
        Button btn_cancel_modify = (Button) findViewById(R.id.btn_cancel_modify);
        btn_cancel_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHOME01();
            }
        });
    }

    private void initList() {
        mArrayFoods = new ArrayList<>();
        int totalPrice = 0;
        Cursor vCursor = getContentResolver().query(Uri.parse(OrderContentProvider.FOOD_URI + "/" + mID), null, null, null, null);
        if (vCursor != null) {
            while (vCursor.moveToNext()) {
                int vId = vCursor.getInt(vCursor.getColumnIndex(FoodHelper._ID));
                String vName = vCursor.getString(vCursor.getColumnIndex(FoodHelper.NAME_FOOD));
                int vQuantity = vCursor.getInt(vCursor.getColumnIndex(FoodHelper.QUANTITY_FOOD));
                int vPrice = vCursor.getInt(vCursor.getColumnIndex(FoodHelper.PRICE_FOOD));
                totalPrice += vQuantity * vPrice;
                mArrayFoods.add(new Food(vId, vName, vQuantity, vPrice));
            }
        }
        setGUI(totalPrice);
    }

    private void modifyOrder() {
        int vQT = 0;
        int vPrice = 0;
        for (int i = 0; i < mArrayFoods.size(); i++) {
            vQT += mArrayFoods.get(i).getmQuantity();
            vPrice += mArrayFoods.get(i).getmQuantity() * mArrayFoods.get(i).getmCost();
        }
        if (vQT > 0) {
            ContentValues vContent = new ContentValues();
            vContent.put(OrderHelper.QUANTITY, vQT);
            vContent.put(OrderHelper.PRICE, vPrice);
            getContentResolver().update(Uri.parse(OrderContentProvider.ORDER_URI + "/" + mID), vContent, null, null);
            for (int i = 0; i < mArrayFoods.size(); i++) {
                Food vFood = mArrayFoods.get(i);
                ContentValues vContent2 = new ContentValues();
                if (vFood.getmQuantity() > 0) {
                    vContent2.put(FoodHelper.NAME_FOOD, vFood.getmFoodName());
                    vContent2.put(FoodHelper.QUANTITY_FOOD, vFood.getmQuantity());
                    vContent2.put(FoodHelper.PRICE_FOOD, vFood.getmCost());
                    getContentResolver().update(Uri.parse(OrderContentProvider.FOOD_URI + "/" + vFood.getmID()), vContent2, null, null);
                } else {
                    getContentResolver().delete(Uri.parse(OrderContentProvider.FOOD_URI + "/" + vFood.getmID()), null, null);
                }
            }
        } else {
            getContentResolver().delete(Uri.parse(OrderContentProvider.ORDER_URI + "/" + mID), null, null);
        }
        backHOME01();
    }

    private void setGUI(int totalPrice) {
        txt_price.setText("TOTALE € " + totalPrice);
    }

    @Override
    public void onYes(int aID) {
        mArrayFoods.get(aID).setmQuantity(0);
        mAdapter.notifyDataSetChanged();
    }

    private void backHOME01() {
        startActivity(new Intent(this, LIST01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
