package com.gabrysuerz.esame_2015.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gabrysuerz.esame_2015.Data.DBHelper;
import com.gabrysuerz.esame_2015.Data.TrainingHelper;
import com.gabrysuerz.esame_2015.R;

public class HOME01 extends AppCompatActivity {

    private TextView txt_count_trainings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);

        txt_count_trainings = (TextView) findViewById(R.id.txt_count_trainings);
        setGUI();

        (findViewById(R.id.btn_to_new01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HOME01.this, NEW01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        (findViewById(R.id.btn_to_list01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HOME01.this, LIST01.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    public void setGUI(){
        txt_count_trainings.setText(""+this.getNumberTrainings());
    }

    private int getNumberTrainings() {
        SQLiteDatabase db = (new DBHelper(getApplicationContext())).getReadableDatabase();
        Cursor c = db.rawQuery(TrainingHelper.COUNT_TRAININGS, null);
        return c.getCount();
    }
}
