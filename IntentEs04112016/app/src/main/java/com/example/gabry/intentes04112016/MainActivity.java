package com.example.gabry.intentes04112016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGUI();
    }

    private void setupGUI() {
        final EditText vEdTxt = (EditText) findViewById(R.id.myTxt);
        Button btnSend = (Button)findViewById(R.id.btnSMS);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickJoke(vEdTxt.getText().toString());
            }
        });
    }

    private void clickJoke(String s) {
        Intent vIntent = new Intent(Intent.ACTION_SEND);
        vIntent.putExtra(Intent.EXTRA_TEXT, s);
        vIntent.setType("text/plain");

        if (vIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(vIntent);
        }
    }


}
