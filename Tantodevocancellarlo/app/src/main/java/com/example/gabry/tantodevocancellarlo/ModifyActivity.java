package com.example.gabry.tantodevocancellarlo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gabry.tantodevocancellarlo.Database.Dataset;
import com.example.gabry.tantodevocancellarlo.Database.Item;
import com.example.gabry.tantodevocancellarlo.Database.ItemAdapter;
import com.example.gabry.tantodevocancellarlo.R;

/**
 * Created by gabry on 13/12/2016.
 */

public class ModifyActivity extends AppCompatActivity {

    Dataset mData;
    ItemAdapter mAdapter;

    private int mID;

    private Button btnMod;
    private EditText firstEdit;
    private EditText secondEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_activity);

        mID = savedInstanceState.getInt(ItemAdapter.BUNID);
        mData = mData.Get(this);
        mAdapter = new ItemAdapter(this, mData.getItems());

        firstEdit = (EditText) findViewById(R.id.firstEdit);
        firstEdit.setText(savedInstanceState.getString(ItemAdapter.BUNFIRST));
        secondEdit = (EditText) findViewById(R.id.secondEdit);
        secondEdit.setText(savedInstanceState.getString(ItemAdapter.BUNSECOND));

        btnMod = (Button) findViewById(R.id.btnDo);
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.modifyItem(new Item(mID, firstEdit.getText().toString(), secondEdit.getText().toString()));
            }
        });
    }
}
