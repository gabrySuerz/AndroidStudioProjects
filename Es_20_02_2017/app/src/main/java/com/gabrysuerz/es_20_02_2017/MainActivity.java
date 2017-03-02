package com.gabrysuerz.es_20_02_2017;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.os.Bundle;
import android.widget.ListView;

import com.gabrysuerz.es_20_02_2017.Data.MyCursorAdapter;

public class MainActivity extends Activity implements BlankFragmentForAsync.IOnAsync, LoaderManager.LoaderCallbacks<Cursor> {

    private boolean mBound;
    private MyService mService;

    private MyCursorAdapter mAdapter;

    private static final String MY_FRAGMENT = "MYFRAGMENT";
    private MyFragment mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // chiamo i metodi di mService

        mAdapter = new MyCursorAdapter(this, null);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);

        mFrag = (MyFragment) getFragmentManager().findFragmentByTag(MY_FRAGMENT);
        if (mFrag == null) {
            mFrag = MyFragment.getInstance();
            getFragmentManager()
                    .beginTransaction()
                    .add(mFrag, MY_FRAGMENT)
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, MyService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder vBinder = (MyService.MyBinder) service;
            mService = vBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public void onUpdate(int value) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
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
