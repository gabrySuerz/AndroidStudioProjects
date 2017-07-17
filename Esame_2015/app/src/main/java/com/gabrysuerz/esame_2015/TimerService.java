package com.gabrysuerz.esame_2015;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class TimerService extends Service {

    public static final String BROADCAST_TIMER = "timer_update";

    private AsyncTimer mAsync;
    private int mSec = 0;
    private int mSecLap = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mAsync = new AsyncTimer();
        if (Build.VERSION.SDK_INT >= 11) {
            mAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            mAsync.execute();
        }
        return new TimerBinder();
    }

    public class TimerBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    public void start() {
    }

    public void stop() {
        mAsync.cancel(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAsync.cancel(true);
    }

    public void setLap() {
        mSecLap = 0;
    }

    public int getSeconds() {
        return mSec;
    }

    public int getLapSeconds() {
        return mSecLap;
    }

    private class AsyncTimer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            LocalBroadcastManager.getInstance(TimerService.this).sendBroadcast(new Intent(BROADCAST_TIMER));
        }

        @Override
        protected String doInBackground(Void... voids) {
            while (!isCancelled()) {
                try {
                    Thread.sleep(1000);
                    mSec++;
                    mSecLap++;
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "FINISH";
        }
    }
}
