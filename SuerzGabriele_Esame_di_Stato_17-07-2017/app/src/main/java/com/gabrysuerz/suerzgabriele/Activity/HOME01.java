package com.gabrysuerz.suerzgabriele.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.Data.FoodHelper;
import com.gabrysuerz.suerzgabriele.Data.OrderContentProvider;
import com.gabrysuerz.suerzgabriele.R;

import java.util.Date;

public class HOME01 extends AppCompatActivity {

    private TextView txt_total_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);

        txt_total_orders = (TextView) findViewById(R.id.txt_total_orders);
        setGUI();

        Button btn_new_order = (Button) findViewById(R.id.btn_new_order);
        btn_new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle vBun = new Bundle();
                vBun.putSerializable(ORD01.DATE, new Date());
                startActivity(new Intent(HOME01.this, ORD01.class).putExtras(vBun).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button btn_modify_order = (Button) findViewById(R.id.btn_modify_order);
        btn_modify_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HOME01.this, LIST01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setGUI();
    }

    private void setGUI() {
        int totalOrders = 0;
        Cursor vCursor = getContentResolver().query(OrderContentProvider.ORDER_URI, null, null, null, null);
        if (vCursor != null) {
            totalOrders = vCursor.getCount();
        }
        txt_total_orders.setText("Totale ordini inseriti: " + totalOrders);
    }
}
