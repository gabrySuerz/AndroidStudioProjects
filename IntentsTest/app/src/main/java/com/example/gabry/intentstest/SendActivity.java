package com.example.gabry.intentstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        TextView txtSend = (TextView)findViewById(R.id.txtSend);

        Intent vIntent = getIntent();
        Bundle vBundle = vIntent.getExtras();
        if(vBundle!=null){
            txtSend.setText(vBundle.getString(Intent.EXTRA_TEXT));
        }

    }
}
