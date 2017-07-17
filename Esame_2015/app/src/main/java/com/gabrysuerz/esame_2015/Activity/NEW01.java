package com.gabrysuerz.esame_2015.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gabrysuerz.esame_2015.R;

public class NEW01 extends AppCompatActivity {

    private EditText edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new01);

        edit_name = (EditText) findViewById(R.id.edit_training_name);

        (findViewById(R.id.btn_to_new02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle vBun=new Bundle();
                vBun.putString(NEW02.NAME, edit_name.getText().toString());
                startActivity(new Intent(NEW01.this, NEW02.class).putExtras(vBun).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    }
}
