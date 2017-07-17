package com.gabrysuerz.esame_2015.Activity;

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

import com.gabrysuerz.esame_2015.Data.TrainingAdapter;
import com.gabrysuerz.esame_2015.Data.TrainingContentProvider;
import com.gabrysuerz.esame_2015.Dialogs.DeleteDialog;
import com.gabrysuerz.esame_2015.R;

public class LIST01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DeleteDialog.IOnDelete {

    private static final String DIALOG_DELETE = "delete";
    private ListView mTrainings;
    private TrainingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);
        mAdapter = new TrainingAdapter(this, null);

        mTrainings = (ListView) findViewById(R.id.list_trainings);
        mTrainings.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        mTrainings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle vBun = new Bundle();
                vBun.putLong(LIST02.ID, l);
                vBun.putString(LIST02.TRAINING, ((TextView) view.findViewById(R.id.txt_training_name)).getText().toString());
                vBun.putString(LIST02.TIME, ((TextView) view.findViewById(R.id.txt_total_time)).getText().toString());
                startActivity(new Intent(LIST01.this, LIST02.class).putExtras(vBun).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        mTrainings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDialog.getInstance(l).show(getFragmentManager(), DIALOG_DELETE);
                return true;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TrainingContentProvider.TRAINING_URI, null, null, null, null);
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
        getContentResolver().delete(Uri.parse(TrainingContentProvider.TRAINING_URI + "/" + aID), null, null);
    }
}
