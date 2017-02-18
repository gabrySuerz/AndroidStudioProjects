package com.example.gabry.tasks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1000;
    private static final String DATE = "date";
    static int NOT_ID = 0;
    private String mCreate;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(DATE, mCreate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Date mDate = new Date();
            mCreate = mDate.toString();
        } else {
            mCreate = savedInstanceState.getString(DATE);
        }
        setupGUI();
    }

    private void setupGUI() {
        Button btnA = (Button) findViewById(R.id.btnA);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchA();
            }
        });

        Button btnB = (Button) findViewById(R.id.btnB);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchB();
            }
        });

        Button btnC = (Button) findViewById(R.id.btnC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchC();
            }
        });

        Button btnD = (Button) findViewById(R.id.btnD);
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchD();
            }
        });

        Button btnMain = (Button) findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMain();
            }
        });

        Button btnNotification = (Button) findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNotification();
            }
        });

        ((TextView) findViewById(R.id.txtDate)).setText(mCreate);
        ((TextView) findViewById(R.id.txtName)).setText(getName());
    }

    protected void launchA() {
        Intent intent = new Intent(this, ActivityA.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    protected void launchB() {
        Intent intent = new Intent(this, ActivityB.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void launchC() {
        Intent intent = new Intent(this, ActivityC.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void launchD() {
        Intent intent = new Intent(this, ActivityD.class);
        startActivity(intent);
    }

    protected void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void launchNotification() {

    }

    protected abstract String getName();

}


