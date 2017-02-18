package com.example.gabrysuerz.es_13_01_2017;

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

import com.example.gabrysuerz.es_13_01_2017.Data.CitiesContentProvider;
import com.example.gabrysuerz.es_13_01_2017.Data.CitiesCursorAdapter;
import com.example.gabrysuerz.es_13_01_2017.Data.CitiesHelper;
import com.example.gabrysuerz.es_13_01_2017.Dialogs.AddDiag;
import com.example.gabrysuerz.es_13_01_2017.Dialogs.DeleteDialog;

public class MainActivity extends AppCompatActivity implements AddDiag.IOnAdd, DeleteDialog.IOnDelete, LoaderManager.LoaderCallbacks<Cursor> {

    CitiesCursorAdapter mAdapter;
    ListView cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = (ListView) findViewById(R.id.cities_list);

        // adapter + loaderManager init

        mAdapter = new CitiesCursorAdapter(this, null);
        cities.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        // UI events

        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetails(id, view);
            }
        });

        cities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog.getInstance(id, "città").show(getSupportFragmentManager(), "DeleteCity");
                return true;
            }
        });

        Button btnAdd = (Button) findViewById(R.id.btn_add_city);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDiag.getInstance("città").show(getSupportFragmentManager(), "NewCity");
            }
        });

    }

    // Start intent

    private void goToDetails(long id, View view) {
        Intent vIntent = new Intent(getApplicationContext(), DetailActivity.class);
        Bundle vBun = new Bundle();
        vBun.putLong(DetailActivity.ID, id);
        TextView str = (TextView) view.findViewById(R.id.txt_city);
        vBun.putString(DetailActivity.NAME, str.getText().toString());
        vIntent.putExtras(vBun);
        startActivity(vIntent);
    }

    // Loader interface implementation

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CitiesContentProvider.CITIES_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    // Dialogs interface implementation

    @Override
    public void onYesAdd(String city) {
        ContentValues content = new ContentValues();
        content.put(CitiesHelper.NAME_CITIES, city);
        getContentResolver().insert(CitiesContentProvider.CITIES_URI, content);
    }

    @Override
    public void onYesDelete(long aId) {
        Log.i("DEL", "" + getContentResolver().delete(Uri.parse(CitiesContentProvider.CITIES_URI + "/" + aId), null, null));
    }
}
