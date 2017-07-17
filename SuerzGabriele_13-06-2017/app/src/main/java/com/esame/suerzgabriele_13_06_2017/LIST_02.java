package com.esame.suerzgabriele_13_06_2017;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.esame.suerzgabriele_13_06_2017.Data.DBHelper;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingContentProvider;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingDetailAdapter;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingDetailHelper;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingHelper;

public class LIST_02 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SESSION = "training_session";
    public static final String CITY = "training_city";
    public static final String TIME = "time_total";

    public TrainingDetailAdapter mAdapter;
    public ListView list;
    private long session;
    private TextView city;
    private TextView time;
    private TextView nLaps;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_02);

        city = (TextView) findViewById(R.id.txt_city);
        nLaps = (TextView) findViewById(R.id.txt_laps);
        time = (TextView) findViewById(R.id.txt_total);
        list = (ListView) findViewById(R.id.list_detail);

        session = getIntent().getExtras().getLong(SESSION);

        dbHelper = new DBHelper(getApplicationContext());

        mAdapter = new TrainingDetailAdapter(this, null);
        list.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        this.setGUI();

    }

    public void setGUI() {
        city.setText(getIntent().getExtras().getString(CITY));
        time.setText(getIntent().getExtras().getString(TIME));
        nLaps.setText("" + this.getCount());
    }

    public int getCount() {
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        Cursor vCursor = vDB.rawQuery(TrainingDetailHelper.laps(session), null);
        return vCursor.getCount();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse(TrainingContentProvider.DETAIL_URI + "/" + session), null, null, null, null);
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
