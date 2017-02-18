package com.example.gabrysuerz.es_13_01_2017;

import android.content.ContentValues;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabrysuerz.es_13_01_2017.Data.CitiesContentProvider;
import com.example.gabrysuerz.es_13_01_2017.Data.DetailedCursorAdapter;
import com.example.gabrysuerz.es_13_01_2017.Data.TempHelper;
import com.example.gabrysuerz.es_13_01_2017.Dialogs.AddDiag;
import com.example.gabrysuerz.es_13_01_2017.Dialogs.DeleteDialog;
import com.example.gabrysuerz.es_13_01_2017.Dialogs.UpdateTempDiag;

import java.util.Date;

public class DetailActivity extends AppCompatActivity implements UpdateTempDiag.IOnUpdateTemp, DeleteDialog.IOnDelete, AddDiag.IOnAdd, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID = "CITY_ID";
    public static final String NAME = "CITY_NAME";

    private long mID;
    private TextView txt_city_name;
    DetailedCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get intent bundle

        Bundle vBun = getIntent().getExtras();
        mID = vBun.getLong(ID);
        String vCity = vBun.getString(NAME);

        // Set GUI

        txt_city_name = (TextView) findViewById(R.id.txt_city_name);
        txt_city_name.setText(vCity);

        // adapter + loaderManager init

        mAdapter = new DetailedCursorAdapter(this, null);
        ListView list = (ListView) findViewById(R.id.temp_list);
        list.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        // UI events

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateTempDiag.getInstance(id).show(getSupportFragmentManager(), "CLICK");
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog.getInstance(id, "temperatura").show(getSupportFragmentManager(), "DeleteCity");
                return true;
            }
        });

        Button btn = (Button) findViewById(R.id.btn_add_temp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDiag.getInstance("temperatura").show(getSupportFragmentManager(), "AddCity");
            }
        });
    }

    // Dialogs interface implementation

    @Override
    public void onYesUpdate(int temp, long id) {
        ContentValues values = new ContentValues();
        values.put(TempHelper.TEMP_CITIES, temp);
        values.put(TempHelper.TEMP_DATE, "" + new Date());
        values.put(TempHelper.TEMP_ID, mID);
        getContentResolver().update(Uri.parse(CitiesContentProvider.CITY_DETAIL + "/" + id), values, null, null);
    }

    @Override
    public void onYesDelete(long aId) {
        getContentResolver().delete(Uri.parse(CitiesContentProvider.CITY_DETAIL + "/" + aId), null, null);
    }

    @Override
    public void onYesAdd(String temp) {
        ContentValues values = new ContentValues();
        values.put(TempHelper.TEMP_CITIES, Integer.parseInt(temp));
        values.put(TempHelper.TEMP_DATE, "" + new Date());
        values.put(TempHelper.TEMP_ID, mID);
        getContentResolver().insert(CitiesContentProvider.CITY_DETAIL, values);
    }

    // Loader interface implementation

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse(CitiesContentProvider.CITY_DETAIL + "/" + mID), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
