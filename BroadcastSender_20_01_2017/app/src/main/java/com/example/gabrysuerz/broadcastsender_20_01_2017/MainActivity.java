package com.example.gabrysuerz.broadcastsender_20_01_2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private int mCounter;
    private BroadcastReceiver mBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCounter++;
            mTextView.setText("" + mCounter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);

        Button vBtn = (Button) findViewById(R.id.btn_action);
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageClick();
            }
        });

        // usando il localBroadcastManager il messaggio rimane limitato all'applicazione
        // quindi non va dichiarato nel manifest

        // qui viene solo dichiarato
        IntentFilter filter = new IntentFilter("TEST");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBR, filter);

    }

    private void manageClick() {
        // potrebbe essere chiamato in qualunque classe
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("TEST"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
