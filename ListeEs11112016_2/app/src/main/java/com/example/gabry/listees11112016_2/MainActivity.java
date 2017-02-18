package com.example.gabry.listees11112016_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NewItem> mList;

    public void initList(int aItems) {
        mList = new ArrayList<NewItem>();
        for (int vIndex = 0; vIndex < aItems; vIndex++) {
            NewItem vItem = new NewItem(vIndex);
            mList.add(vItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList(500);
        CustomAdapter vCustom = new CustomAdapter(this, mList);

        ListView vList = (ListView) findViewById(R.id.listView);
        vList.setAdapter(vCustom);

    }
}
