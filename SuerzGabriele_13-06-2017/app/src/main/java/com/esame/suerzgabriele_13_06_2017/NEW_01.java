package com.esame.suerzgabriele_13_06_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NEW_01 extends AppCompatActivity {

    EditText edit_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_01);

        edit_city = (EditText) findViewById(R.id.edit_city);

        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(getApplicationContext(), NEW_02.class);
                Bundle vBundle = new Bundle();
                vBundle.putString(NEW_02.CITY, edit_city.getText().toString());
                vIntent.putExtras(vBundle);
                startActivity(vIntent);
            }
        });
    }
}
