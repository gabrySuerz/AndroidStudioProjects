package com.esame.suerzgabriele.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esame.suerzgabriele.Data.ReservationHelper;
import com.esame.suerzgabriele.R;

public class Activity3 extends Activity {

    EditText edit_phone;
    Bundle mBundle;
    Integer person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        mBundle = getIntent().getExtras();
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        person = mBundle.getInt(ReservationHelper.PERSON);

        setGUI();

        Button btn_back = (Button) findViewById(R.id.btn_back3);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity2.class);

                String phone = edit_phone.getText().toString();
                if (phone != null)
                    mBundle.putString(ReservationHelper.PERSON, phone);
                if(person!=null)
                    mBundle.putInt(ReservationHelper.PERSON, person);

                intent.putExtras(mBundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel3);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button btn_go = (Button) findViewById(R.id.btn_go3);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(getApplicationContext(), Activity4.class);
                mBundle.putString(ReservationHelper.PHONE, edit_phone.getText().toString());
                vIntent.putExtras(mBundle);
                startActivity(vIntent);
            }
        });
    }

    private void setGUI() {
        if (mBundle != null && mBundle.getString(ReservationHelper.PHONE) != "") {
            edit_phone.setText(mBundle.getString(ReservationHelper.PHONE));
        }
    }
}
