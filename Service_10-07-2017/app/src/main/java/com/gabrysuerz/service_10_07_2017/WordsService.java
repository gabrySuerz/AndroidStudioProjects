package com.gabrysuerz.service_10_07_2017;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by gabrysuerz on 10/07/17.
 */

public class WordsService extends Service {

    public static final String BROADCAST = "finished_operation";
    public Binder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startService() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROADCAST));
    }

    public String[] getWords() {
        String[] array = {"Hey", "Come", "Va", "Oggi", "?"};
        return array;
    }

    public class MyBinder extends Binder {
        public WordsService getService() {
            return WordsService.this;
        }
    }
}
