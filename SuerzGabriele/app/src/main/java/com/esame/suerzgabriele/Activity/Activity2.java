package com.esame.suerzgabriele.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esame.suerzgabriele.Data.ReservationHelper;
import com.esame.suerzgabriele.R;

public class Activity2 extends Activity {

    EditText edit_name;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edit_name = (EditText) findViewById(R.id.edit_name);

        if (getIntent().getExtras() != null) {
            mBundle = getIntent().getExtras();
        } else {
            mBundle = new Bundle();
        }
        setGUI();

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel2);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button btn_go = (Button) findViewById(R.id.btn_go2);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(getApplicationContext(), Activity3.class);
                mBundle.putString(ReservationHelper.NAME, edit_name.getText().toString());
                vIntent.putExtras(mBundle);
                startActivity(vIntent);
            }
        });
    }

    private void setGUI() {
        if (mBundle != null && mBundle.getString(ReservationHelper.NAME) != null) {
            edit_name.setText(mBundle.getString(ReservationHelper.NAME));
        }
    }
}
