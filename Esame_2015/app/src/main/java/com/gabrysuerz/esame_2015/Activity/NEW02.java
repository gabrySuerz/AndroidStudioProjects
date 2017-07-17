package com.gabrysuerz.esame_2015.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.widget.TextView;

import com.gabrysuerz.esame_2015.Data.LapHelper;
import com.gabrysuerz.esame_2015.Data.TrainingContentProvider;
import com.gabrysuerz.esame_2015.Data.TrainingHelper;
import com.gabrysuerz.esame_2015.R;
import com.gabrysuerz.esame_2015.TimerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

public class NEW02 extends AppCompatActivity {

    public static final String NAME = "training_name";

    private TextView txt_training_name;
    private TextView txt_total_time;
    private TextView txt_lap_time;

    private TimerService mService;
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerBinder binder = (TimerService.TimerBinder) iBinder;
            mService = binder.getService();
            //mService.start();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //mService = null;
        }
    };
    private BroadcastReceiver mBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            txt_total_time.setText(sdf.format(new Date(mService.getSeconds() * 1000)));
            txt_lap_time.setText(sdf.format(new Date(mService.getLapSeconds() * 1000)));
        }
    };

    private ArrayList<Integer> mArrayLaps = new ArrayList<>();
    private String mName;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new02);

        txt_training_name = (TextView) findViewById(R.id.txt_training_name);
        txt_total_time = (TextView) findViewById(R.id.txt_total_time);
        txt_lap_time = (TextView) findViewById(R.id.txt_timer);

        setGUI();
        bindService(new Intent(this, TimerService.class), mConnection, BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBr, new IntentFilter(TimerService.BROADCAST_TIMER));

        (findViewById(R.id.btn_lap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArrayLaps.add(mService.getLapSeconds());
                mService.setLap();
            }
        });

        (findViewById(R.id.btn_stop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArrayLaps.add(mService.getLapSeconds());
                save();
            }
        });

        (findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void setGUI() {
        mName = getIntent().getExtras().getString(NAME);
        txt_training_name.setText(mName);
    }

    private void save() {
        ContentValues cont1 = new ContentValues();
        cont1.put(TrainingHelper.TRAINING, mName);
        Log.d("TAG", "save: "+new Date().getTime());
        cont1.put(TrainingHelper.DATE, new Date().getTime());
        cont1.put(TrainingHelper.TIME, mService.getSeconds());
        Uri vUri= getContentResolver().insert(TrainingContentProvider.TRAINING_URI, cont1);
        for (int i = 0; i < mArrayLaps.size(); i++) {
            ContentValues cont2 = new ContentValues();
            cont2.put(LapHelper.NUMBER, i + 1);
            cont2.put(LapHelper.TIME, mArrayLaps.get(i));
            cont2.put(LapHelper.SESSION, vUri.getLastPathSegment());
            getContentResolver().insert(TrainingContentProvider.LAPS_URI, cont2);
        }
        back();
    }

    private void back() {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBr);
        }
        startActivity(new Intent(this, HOME01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
