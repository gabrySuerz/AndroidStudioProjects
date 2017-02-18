package com.example.gabry.contentprovider_16_12_2016;

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

import com.example.gabry.contentprovider_16_12_2016.Data.ContactContentProvider;
import com.example.gabry.contentprovider_16_12_2016.Data.ContactCursorAdapter;
import com.example.gabry.contentprovider_16_12_2016.Data.ContactsHelper;

import java.util.Random;

import static com.example.gabry.contentprovider_16_12_2016.R.id.btn_add;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DeleteDialog.IOnDelete, UpdateDialog.IOnUpdate {

    public static final String UP_ID = "update";
    ContactCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView vList = (ListView) findViewById(R.id.items_list);

        mAdapter = new ContactCursorAdapter(this, null);
        vList.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateDialog updateDialog = UpdateDialog.getInstance(id);
                updateDialog.show(getFragmentManager(), "UpdateButton");
            }
        });

        vList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog delDiag = DeleteDialog.getInstance(id);
                delDiag.show(getFragmentManager(), "DeleteButton");
                return false;
            }
        });

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void deleteItem(long aId) {
        Uri uriToDelete = Uri.parse(ContactContentProvider.CONTACTS_URI + "/" + aId);
        getContentResolver().delete(uriToDelete, null, null);
    }

    private void addItem() {
        Random vRand = new Random();
        ContentValues vValues = new ContentValues();
        int i = vRand.nextInt();
        vValues.put(ContactsHelper.NAME, "Name " + i);
        vValues.put(ContactsHelper.SURNAME, "Surname " + i);

        getContentResolver().insert(ContactContentProvider.CONTACTS_URI, vValues);
    }

    private void updateItem(long aId) {
        Intent vIntent = new Intent(this, UpdateActivity.class);
        Bundle vBundle = new Bundle();
        vBundle.putLong(UP_ID, aId);
        vIntent.putExtras(vBundle);
        startActivity(vIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactContentProvider.CONTACTS_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onYesDelete(long aId) {
        deleteItem(aId);
    }


    @Override
    public void onYesUpdate(long aId) {
        updateItem(aId);
    }
}
