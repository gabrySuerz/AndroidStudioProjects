package com.esame.suerzgabriele_13_06_2017;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esame.suerzgabriele_13_06_2017.Data.TrainingContentProvider;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingDetailHelper;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NEW_02 extends AppCompatActivity implements TIMER.IOnTimer {

    private static final String TIMER_FRAGMENT = "Timer";
    private static final String LAP_TIME = "lap_time";
    public static final String CITY = "txt_city";
    private static final String TOTAL_TIME = "total_time";
    private static final String LAPS = "laps";
    private static final String TAG = "log";

    private TextView txt_city, txt_total_time, txt_lap_time;
    private TIMER vTIMERFrag;
    private HashMap<Integer, String> laps = new HashMap<>();
    private String lapTime;

    private int secondsTimer;
    private TIMER_SERVICE serviceT;
    private BroadcastReceiver mBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            secondsTimer = serviceT.getSeconds();
            SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
            Log.i(TAG, "onReceive: " + sdf.format(new Date((secondsTimer - 3600) * 1000L)));
        }
    };

    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            TIMER_SERVICE.TimerBinder myBinder = (TIMER_SERVICE.TimerBinder) binder;
            serviceT = myBinder.getService();
            mBound = true;
            serviceT.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceT = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_02);

        txt_city = (TextView) findViewById(R.id.txt_city);
        txt_total_time = (TextView) findViewById(R.id.txt_total_time);
        txt_lap_time = (TextView) findViewById(R.id.txt_lap_time);
        vTIMERFrag = (TIMER) getFragmentManager().findFragmentByTag(TIMER_FRAGMENT);

        if (savedInstanceState != null) {
            txt_lap_time.setText(savedInstanceState.getString(LAP_TIME));
            txt_total_time.setText(savedInstanceState.getString(TOTAL_TIME));
            laps = (HashMap<Integer, String>) savedInstanceState.getSerializable(LAPS);
        }

        if (vTIMERFrag == null) {
            vTIMERFrag = TIMER.getInstance();
            getFragmentManager()
                    .beginTransaction()
                    .add(vTIMERFrag, TIMER_FRAGMENT)
                    .commit();
            vTIMERFrag.start();
        }


        txt_city.setText(getIntent().getExtras().getString(CITY));

        Button btn_lap = (Button) findViewById(R.id.btn_lap);
        btn_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laps.put(laps.size() + 1, lapTime);
                vTIMERFrag.lapStop();
            }
        });

        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laps.put(laps.size() + 1, lapTime);
                vTIMERFrag.cancel(false);
                if (mBound) {
                    unbindService(mConnection);
                    mBound = false;
                }
                LocalBroadcastManager.getInstance(NEW_02.this).unregisterReceiver(mBr);
                stopService(new Intent(NEW_02.this, TIMER_SERVICE.class));
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vTIMERFrag.cancel(true);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(NEW_02.this, TIMER_SERVICE.class), mConnection, BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(NEW_02.this).registerReceiver(mBr, new IntentFilter(TIMER_SERVICE.TIMER_UPDATE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBr);
        }
    }

    @Override
    public void onUpdateTimer(int min, int sec) {
        txt_total_time.setText("" + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    @Override
    public void onLapTimer(int min, int sec) {
        lapTime = "" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        txt_lap_time.setText(lapTime);
    }

    @Override
    public void onStopTimer() {
        ContentValues values = new ContentValues();
        values.put(TrainingHelper.CITY, txt_city.getText().toString());

        String formatTime = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);

        values.put(TrainingHelper.TIME, txt_total_time.getText().toString());
        values.put(TrainingHelper.DATE, sdf.format(new Date()));
        Uri uri = getContentResolver().insert(TrainingContentProvider.TRAINING_URI, values);
        if (laps.size() > 0) {
            for (Integer key : laps.keySet()) {
                ContentValues val = new ContentValues();
                val.put(TrainingDetailHelper.N_LAPS, key);
                val.put(TrainingDetailHelper.TIME, laps.get(key));
                val.put(TrainingDetailHelper.SESSION, uri.getLastPathSegment());
                getContentResolver().insert(TrainingContentProvider.DETAIL_URI, val);
            }
        }
        Intent vIntent = new Intent(getApplicationContext(), HOME_01.class);
        vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
    }

    @Override
    public void onCancelTimer() {
        Intent vIntent = new Intent(getApplicationContext(), HOME_01.class);
        vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAP_TIME, txt_lap_time.getText().toString());
        outState.putString(TOTAL_TIME, txt_total_time.getText().toString());
        outState.putSerializable(LAPS, laps);
    }
}
