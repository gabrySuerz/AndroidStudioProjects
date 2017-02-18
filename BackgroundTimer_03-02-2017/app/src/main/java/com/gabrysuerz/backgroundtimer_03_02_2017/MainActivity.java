package com.gabrysuerz.backgroundtimer_03_02_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimerFragment.IOnTimerUpdate {

    TextView mText;
    private static final String TIMER_FRAGMENT = " timer_fragment";
    TimerFragment vFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.txt);

        vFrag = (TimerFragment) getSupportFragmentManager().findFragmentByTag(TIMER_FRAGMENT);
        if (vFrag == null) {
            vFrag = TimerFragment.getInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(vFrag, TIMER_FRAGMENT)
                    .commit();
        }

        Button btnStart = (Button) findViewById(R.id.btn);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vFrag.start();
            }
        });

        Button btnStop = (Button) findViewById(R.id.btn2);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vFrag.delete();
            }
        });

    }

    @Override
    public void onUpdateTimer(int hour, int min, int sec) {
        mText.setText("" + String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    @Override
    public void onCancelTimer() {
        mText.setText("00:00:00");
    }


}