package com.esame.suerzgabriele_13_06_2017;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by gabrysuerz on 13/07/17.
 */

public class TIMER_SERVICE extends Service {

    public static final String TIMER_UPDATE = "timer_update";
    private TimerAsync async;
    private int sec = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TimerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class TimerBinder extends Binder {
        public TIMER_SERVICE getService() {
            return TIMER_SERVICE.this;
        }
    }

    public void start(){
        async = new TimerAsync();
        if(Build.VERSION.SDK_INT >= 11) {
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            async.execute();
        }
    }

    public void stop(){
        async.cancel(true);
    }

    public int getSeconds() {
        return sec;
    }

    public class TimerAsync extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            LocalBroadcastManager.getInstance(TIMER_SERVICE.this).sendBroadcast(new Intent(TIMER_UPDATE));
        }

        @Override
        protected String doInBackground(Void... params) {
            while (!isCancelled()) {
                try {
                    Thread.sleep(1000);
                    sec++;
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "FINISH";
        }
    }

}
