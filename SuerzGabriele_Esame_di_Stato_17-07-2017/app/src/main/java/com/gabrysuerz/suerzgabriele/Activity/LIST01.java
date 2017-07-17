package com.gabrysuerz.suerzgabriele.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrysuerz.suerzgabriele.Data.OrderAdapter;
import com.gabrysuerz.suerzgabriele.Data.OrderContentProvider;
import com.gabrysuerz.suerzgabriele.Dialogs.DeleteDialog;
import com.gabrysuerz.suerzgabriele.R;

public class LIST01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DeleteDialog.IOnConfirm {

    private static final String DELETE_TAG = "delete";
    ListView list_orders;
    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);

        mAdapter = new OrderAdapter(this, null);

        list_orders = (ListView) findViewById(R.id.list_orders);
        list_orders.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        list_orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle vBun = new Bundle();
                vBun.putLong(ORD02.ID, l);
                startActivity(new Intent(LIST01.this, ORD02.class).putExtras(vBun).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        list_orders.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDialog.getInstance(((TextView) view.findViewById(R.id.txt_date_order_list)).getText().toString(), l).show(getSupportFragmentManager(), DELETE_TAG);
                return true;
            }
        });

        Button btn_backHOME01 = (Button) findViewById(R.id.btn_back_HOME01);
        btn_backHOME01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHOME01();
            }
        });
    }

    private void backHOME01() {
        startActivity(new Intent(this, HOME01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, OrderContentProvider.ORDER_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onYes(long aID) {
        getContentResolver().delete(Uri.parse(OrderContentProvider.ORDER_URI + "/" + aID), null, null);
    }
}
