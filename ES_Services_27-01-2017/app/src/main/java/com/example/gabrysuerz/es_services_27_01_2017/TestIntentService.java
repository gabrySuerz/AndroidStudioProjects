package com.example.gabrysuerz.es_services_27_01_2017;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by gabrysuerz on 27/01/17.
 */

public class TestIntentService extends IntentService {

    private static final String TAG = "TAG";

    public TestIntentService() {
        super("Qualcosa");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "IntentService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "IntentService onStartCommand");
        Bundle vBun = intent.getExtras();
        if (vBun != null) {
            String vStr = vBun.getString("KEY");
            Log.i(TAG, vStr);
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "IntentService onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "IntentService onHandleIntent");
        Bundle vBun = intent.getExtras();
        if (vBun != null) {
            String vStr = vBun.getString("KEY");
            Log.i(TAG, vStr);
            timerLog();
        }
    }

    private void timerLog() {
        try {
            for (int vCount = 0; vCount < 150; vCount++) {
                Thread.sleep(100);
                Log.i(TAG, "TIMER: " + vCount);
            }
            stopSelf();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("TIME_ENDED"));
    }
}
