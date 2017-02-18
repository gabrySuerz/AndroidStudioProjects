package com.example.gabrysuerz.services_es_13_01_2017;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private RandomService mService;
    private boolean mBound;
    TextView txtRan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRan = (TextView) findViewById(R.id.txt_Random);

        Button btnRan = (Button) findViewById(R.id.btn_Random);
        btnRan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRan.setText("" + mService.getRandomNumber());

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, RandomService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RandomService.MyBinder vBinder = (RandomService.MyBinder) service;
            mService = vBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
}
