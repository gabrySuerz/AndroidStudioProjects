package com.example.gabry.contentprovider_16_12_2016;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gabry.contentprovider_16_12_2016.Data.ContactContentProvider;
import com.example.gabry.contentprovider_16_12_2016.Data.ContactsHelper;

/**
 * Created by gabrysuerz on 23/12/16.
 */

public class UpdateActivity extends AppCompatActivity {

    EditText edit_Name;
    EditText edit_Surname;
    long vId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        vId = getIntent().getLongExtra(MainActivity.UP_ID,vId);
        edit_Name = (EditText) findViewById(R.id.edit_name);
        edit_Surname = (EditText) findViewById(R.id.edit_surname);
        setGui(vId);

        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(vId);
                finish();
            }
        });

    }

    private void setGui(long aId) {
        Cursor vCursor = getContentResolver().query(Uri.parse(ContactContentProvider.CONTACTS_URI + "/" + aId), null, null, null, null);
        vCursor.moveToFirst();
        edit_Name.setText("" + vCursor.getString(vCursor.getColumnIndex(ContactsHelper.NAME)));
        edit_Surname.setText("" + vCursor.getString(vCursor.getColumnIndex(ContactsHelper.SURNAME)));
        vCursor.close();
    }

    private void update(long aId) {
        ContentValues vValues = new ContentValues();

        vValues.put(ContactsHelper.NAME, edit_Name.getText().toString());
        vValues.put(ContactsHelper.SURNAME, edit_Surname.getText().toString());

        getContentResolver().update(Uri.parse(ContactContentProvider.CONTACTS_URI + "/" + aId), vValues, null, null);

    }
}
