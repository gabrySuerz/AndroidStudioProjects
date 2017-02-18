package com.example.gabry.datasource_02_12_2016;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.gabry.datasource_02_12_2016.data.ContactsAdapter;
import com.example.gabry.datasource_02_12_2016.data.Contact;
import com.example.gabry.datasource_02_12_2016.data.Dataset;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.IOnDelete {

    private static final String LIST = "AllRight";
    private Dataset mDataset;
    ContactsAdapter mAdapter;
    private long mID;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataset = Dataset.Get(this);

        final ListView list = (ListView) findViewById(R.id.list);

        if (savedInstanceState != null) {
            //mAdapter = new ContactsAdapter(this,);
        } else {
            mAdapter = new ContactsAdapter(this, mDataset.getContacts());
        }

        list.setAdapter(mAdapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mID = id;
                Dialog delDiag = Dialog.getInstance();
                delDiag.show(getSupportFragmentManager(), "DelButton");
                return false;
            }
        });

        Button btn_add = (Button) findViewById(R.id.btnAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.addContact(Contact.createContacts());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onYes() {
        mDataset.removeContact(mID);
        mAdapter.notifyDataSetChanged();
    }
}
