package com.esame.suerzgabriele_13_06_2017;

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
import android.widget.ListView;
import android.widget.TextView;

import com.esame.suerzgabriele_13_06_2017.Data.TrainingAdapter;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingContentProvider;

public class LIST_01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DeleteDialog.IOnDelete {

    TrainingAdapter mAdapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_01);

        list = (ListView) findViewById(R.id.list_training);

        mAdapter = new TrainingAdapter(this, null);
        list.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), LIST_02.class);
                Bundle bundle = new Bundle();
                bundle.putLong(LIST_02.SESSION, id);
                bundle.putString(LIST_02.CITY, ((TextView) view.findViewById(R.id.txt_city)).getText().toString());
                bundle.putString(LIST_02.TIME, ((TextView) view.findViewById(R.id.txt_time)).getText().toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog.getInstance(id).show(getFragmentManager(), "DeleteSession");
                return true;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TrainingContentProvider.TRAINING_URI, null, null, null, null);
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
    public void onDelete(long id) {
        getContentResolver().delete(Uri.parse(TrainingContentProvider.TRAINING_URI + "/" + id), null, null);
    }
}
