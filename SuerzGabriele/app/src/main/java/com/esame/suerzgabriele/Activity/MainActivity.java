package com.esame.suerzgabriele.Activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.esame.suerzgabriele.Data.ReservationContentProvider;
import com.esame.suerzgabriele.Data.ReservationCursorAdapter;
import com.esame.suerzgabriele.Dialog.DeleteDialog;
import com.esame.suerzgabriele.R;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, DeleteDialog.IOnDelete {

    private ReservationCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ReservationCursorAdapter(this, null);
        ListView vList = (ListView) findViewById(R.id.list);
        vList.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);

        vList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog.getInstance(id).show(getFragmentManager(), "DELETE_RESERVATION");
                return true;
            }
        });

        Button btn_startInsert = (Button) findViewById(R.id.btn_add);
        btn_startInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity2.class));
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ReservationContentProvider.RESERVATION_URI, null, null, null, null);
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
    public void onYes(long id) {
        getContentResolver().delete(Uri.parse(ReservationContentProvider.RESERVATION_URI + "/" + id), null, null);
    }
}
