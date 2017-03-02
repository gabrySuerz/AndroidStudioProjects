package com.example.gabrysuerz.es_services_27_01_2017;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by gabrysuerz on 27/01/17.
 */

public class TestService extends Service {

    private static final String TAG = "TAG";
    MyBinder mBinder = new MyBinder();

    BroadcastReceiver mBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            timerLog();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        Bundle vBun = intent.getExtras();
        if (vBun != null) {
            String vStr = vBun.getString("KEY");
            Log.i(TAG, vStr);
        }
        IntentFilter vFilter = new IntentFilter("START_LONG");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcast, vFilter);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcast);
    }

    @Nullable
    @Override
    public MyBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service onUnbind");
        return super.onUnbind(intent);
    }

    private void timerLog() {
        try {
            for (int vCount = 0; vCount < 10; vCount++) {
                Thread.sleep(100);
                Log.i(TAG, "TIMER: " + vCount);
            }
            stopSelf();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public class MyBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }
}
