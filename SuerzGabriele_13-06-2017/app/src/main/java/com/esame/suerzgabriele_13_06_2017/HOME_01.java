package com.esame.suerzgabriele_13_06_2017;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esame.suerzgabriele_13_06_2017.Data.DBHelper;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingContentProvider;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingDetailHelper;
import com.esame.suerzgabriele_13_06_2017.Data.TrainingHelper;

public class HOME_01 extends AppCompatActivity {

    TextView nLaps;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_01);

        dbHelper = new DBHelper(getApplicationContext());

        nLaps = (TextView) findViewById(R.id.txt_training_n);
        setGUI();

        Button btn_new = (Button) findViewById(R.id.btn_new_training);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(getApplicationContext(), NEW_01.class);
                startActivity(vIntent);
            }
        });

        Button btn_history = (Button) findViewById(R.id.btn_training_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(getApplicationContext(), LIST_01.class);
                startActivity(vIntent);
            }
        });
    }

    public void setGUI() {
        nLaps.setText("" + this.getCount());
    }

    public int getCount() {
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        Cursor vCursor = vDB.rawQuery(TrainingHelper.COUNT_TRAININGS, null);
        return vCursor.getCount();
    }
}
