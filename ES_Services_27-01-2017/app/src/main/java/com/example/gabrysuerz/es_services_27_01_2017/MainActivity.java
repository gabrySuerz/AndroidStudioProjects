package com.example.gabrysuerz.es_services_27_01_2017;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView mTxt;
    int mCounter = 1;
    private boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "ServiceConnection onServiceConnected");
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "ServiceConnection onServiceDisconnected");
        }
    };

    BroadcastReceiver mBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTxt.setText("Finito");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxt = (TextView) findViewById(R.id.txt);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxt.setText("" + mCounter++);
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent("START_LONG"));
            }
        });
    }

    Intent mStartIntent;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Activity onStart");

        IntentFilter vFilter = new IntentFilter("TIME_ENDED");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcast, vFilter);

        mStartIntent = new Intent(this, TestIntentService.class);
        Bundle vBun = new Bundle();
        vBun.putString("KEY", "Heila");
        mStartIntent.putExtras(vBun);

        startService(mStartIntent);
        //bounded service
        //bindService(new Intent(this, TestService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //bounded service
        /*if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }*/
        stopService(mStartIntent);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcast);
        Log.i(TAG, "Activity onStop");
    }
}
