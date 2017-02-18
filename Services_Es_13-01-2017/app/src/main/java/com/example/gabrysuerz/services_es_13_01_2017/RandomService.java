package com.example.gabrysuerz.services_es_13_01_2017;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * Created by gabrysuerz on 13/01/17.
 */

public class RandomService extends Service {

    MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber(){
        Random vRand=new Random();
        return vRand.nextInt();
    }

    public class MyBinder extends Binder {
        public RandomService getService() {
            return RandomService.this;
        }
    }
}
