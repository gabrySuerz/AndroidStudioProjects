package com.gabrysuerz.es_20_02_2017;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class MyService extends Service {

    MyBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //TODO methods

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    // mettere nel manifest
    // <service android:name=".MyService"></service>

}
