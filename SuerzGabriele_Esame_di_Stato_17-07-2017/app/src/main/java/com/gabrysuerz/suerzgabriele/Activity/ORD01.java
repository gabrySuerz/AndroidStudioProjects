package com.gabrysuerz.suerzgabriele.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.Data.Food;
import com.gabrysuerz.suerzgabriele.Data.FoodHelper;
import com.gabrysuerz.suerzgabriele.Data.FoodsListAdapter;
import com.gabrysuerz.suerzgabriele.Data.OrderContentProvider;
import com.gabrysuerz.suerzgabriele.Data.OrderHelper;
import com.gabrysuerz.suerzgabriele.Dialogs.ClearDialog;
import com.gabrysuerz.suerzgabriele.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ORD01 extends AppCompatActivity implements ClearDialog.IOnConfirm {

    private static final String CLEAR_TAG = "delete";
    private static final String LIST = "list";
    public static final String DATE = "date";

    private TextView txt_date;
    private ListView list_foods;

    private FoodsListAdapter mAdapter;
    private ArrayList<Food> mFoodsArray;
    private Date mOrderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord01);

        if (savedInstanceState != null) {
            mFoodsArray = (ArrayList<Food>) savedInstanceState.getSerializable(LIST);
        } else {
            initList();
        }

        mAdapter = new FoodsListAdapter(this, mFoodsArray);

        txt_date = (TextView) findViewById(R.id.txt_date);
        list_foods = (ListView) findViewById(R.id.list_foods);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        mOrderDate = (Date) getIntent().getExtras().getSerializable(DATE);
        txt_date.setText(sdf.format(mOrderDate));

        list_foods.setAdapter(mAdapter);

        list_foods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food vFood = mFoodsArray.get(i);
                vFood.setmQuantity(vFood.getmQuantity() + 1);
                mAdapter.notifyDataSetChanged();
            }
        });
        list_foods.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food vFood = (Food) adapterView.getItemAtPosition(i);
                ClearDialog.getInstance(vFood.getmFoodName(), i).show(getSupportFragmentManager(), CLEAR_TAG);
                return true;
            }
        });


        Button btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHOME01();
            }
        });

        Button btn_confirm_order = (Button) findViewById(R.id.btn_confirm_order);
        btn_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrder();
            }
        });
    }

    private void initList() {
        mFoodsArray = new ArrayList<>();
        mFoodsArray.add(new Food(0, "Pizza", 0, 8));
        mFoodsArray.add(new Food(1, "Panino", 0, 6));
        mFoodsArray.add(new Food(2, "Bibita", 0, 3));
        mFoodsArray.add(new Food(3, "Gelato", 0, 3));
        mFoodsArray.add(new Food(4, "Caffè", 0, 1));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST, mFoodsArray);
    }

    private void saveOrder() {
        ContentValues vContent = new ContentValues();
        int vQT = 0;
        int vPrice = 0;
        for (int i = 0; i < mFoodsArray.size(); i++) {
            vQT += mFoodsArray.get(i).getmQuantity();
            vPrice += mFoodsArray.get(i).getmQuantity() * mFoodsArray.get(i).getmCost();
        }
        if (vQT > 0) {
            vContent.put(OrderHelper.DATE, mOrderDate.getTime());
            vContent.put(OrderHelper.QUANTITY, vQT);
            vContent.put(OrderHelper.PRICE, vPrice);
            Uri uri = getContentResolver().insert(OrderContentProvider.ORDER_URI, vContent);
            for (int i = 0; i < mFoodsArray.size(); i++) {
                Food vFood = mFoodsArray.get(i);
                ContentValues vContent2 = new ContentValues();
                if (vFood.getmQuantity() > 0) {
                    vContent2.put(FoodHelper.NAME_FOOD, vFood.getmFoodName());
                    vContent2.put(FoodHelper.QUANTITY_FOOD, vFood.getmQuantity());
                    vContent2.put(FoodHelper.PRICE_FOOD, vFood.getmCost());
                    vContent2.put(FoodHelper.FOREIGN_KEY, uri.getLastPathSegment());
                    getContentResolver().insert(OrderContentProvider.FOOD_URI, vContent2);
                }
            }
        } else {

        }
        backHOME01();
    }

    private void backHOME01() {
        startActivity(new Intent(this, HOME01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onYes(int aID) {
        mFoodsArray.get(aID).setmQuantity(0);
        mAdapter.notifyDataSetChanged();
    }
}
