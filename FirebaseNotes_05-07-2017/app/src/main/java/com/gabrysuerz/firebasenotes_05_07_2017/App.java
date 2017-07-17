package com.gabrysuerz.firebasenotes_05_07_2017;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabrysuerz on 06/07/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
