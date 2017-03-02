package com.esame.suerzgabriele.Activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esame.suerzgabriele.Data.ReservationContentProvider;
import com.esame.suerzgabriele.Data.ReservationHelper;
import com.esame.suerzgabriele.R;

public class Activity4 extends Activity {

    ContentValues values;
    EditText edit_person;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        mBundle = getIntent().getExtras();
        values = new ContentValues();
        edit_person = (EditText) findViewById(R.id.edit_person);

        setGUI();

        values.put(ReservationHelper.NAME, mBundle.getString(ReservationHelper.NAME));
        values.put(ReservationHelper.PHONE, mBundle.getString(ReservationHelper.PHONE));

        Button btn_back = (Button) findViewById(R.id.btn_back4);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity3.class);

                if (edit_person.getText().toString() != "") {
                } else {
                    mBundle.putInt(ReservationHelper.PERSON, Integer.parseInt(edit_person.getText().toString()));
                }

                intent.putExtras(mBundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel4);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button btn_save = (Button) findViewById(R.id.btn_save4);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put(ReservationHelper.PERSON, Integer.parseInt(edit_person.getText().toString()));
                getContentResolver().insert(ReservationContentProvider.RESERVATION_URI, values);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setGUI() {
        if (mBundle != null) {
            if (mBundle.getInt(ReservationHelper.PERSON) > 0) {
                edit_person.setText("" + mBundle.getInt(ReservationHelper.PERSON));
            }
        }
    }
}
