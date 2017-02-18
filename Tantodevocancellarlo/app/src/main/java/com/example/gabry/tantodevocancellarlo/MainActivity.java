package com.example.gabry.tantodevocancellarlo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.gabry.tantodevocancellarlo.Database.Dataset;
import com.example.gabry.tantodevocancellarlo.Database.Item;
import com.example.gabry.tantodevocancellarlo.Database.ItemAdapter;

public class MainActivity extends AppCompatActivity implements DeleteDialog.IOnDelete{

    Dataset mData;
    ItemAdapter mAdapter;
    private long mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = mData.Get(this);
        final ListView list = (ListView) findViewById(R.id.itemList);
        mAdapter = new ItemAdapter(this, mData.getItems());
        list.setAdapter(mAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mID=id;
                Log.d("no","lvsdkjbjsdc");
                DeleteDialog.getInstance();
                return false;
            }
        });

        Button btnNewItem = (Button) findViewById(R.id.btnNew);
        btnNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.addItem(Item.createItem());
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onYes() {
        mData.deleteItem(mID);
        mAdapter.notifyDataSetChanged();
    }
}
