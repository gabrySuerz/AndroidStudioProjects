package com.gabrysuerz.esame_2015.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.gabrysuerz.esame_2015.Data.DBHelper;
import com.gabrysuerz.esame_2015.Data.LapAdapter;
import com.gabrysuerz.esame_2015.Data.LapHelper;
import com.gabrysuerz.esame_2015.Data.TrainingContentProvider;
import com.gabrysuerz.esame_2015.Data.TrainingHelper;
import com.gabrysuerz.esame_2015.R;

public class LIST02 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID = "id_training";
    public static final String TRAINING = "training_city";
    public static final String TIME = "time_total";

    private ListView mLaps;
    private LapAdapter mAdapter;

    private TextView txt_laps;
    private TextView txt_total_time;
    private TextView txt_training_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list02);
        mAdapter = new LapAdapter(this, null);

        txt_laps = (TextView) findViewById(R.id.txt_laps);
        txt_total_time = (TextView) findViewById(R.id.txt_total_time);
        txt_training_name = (TextView) findViewById(R.id.txt_training_name);
        setGUI();

        mLaps = (ListView) findViewById(R.id.list_laps);
        mLaps.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void setGUI() {
        txt_training_name.setText(getIntent().getExtras().getString(TRAINING));
        txt_total_time.setText(getIntent().getExtras().getString(TIME));
        txt_laps.setText("" + getLapsCount());
    }

    private int getLapsCount() {
        SQLiteDatabase db = (new DBHelper(getApplicationContext())).getReadableDatabase();
        Cursor c = db.rawQuery(LapHelper.COUNT_LAPS(getIntent().getExtras().getLong(ID)), null);
        return c.getCount();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse(TrainingContentProvider.LAPS_URI + "/" + getIntent().getExtras().getLong(ID)), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
