package com.example.gabrysuerz.anagrafiche_es_19_01_2017;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data.PersonContentProvider;
import com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data.PersonHelper;

public class AddPersonActivity extends AppCompatActivity {

    EditText name, surname, date, email, phone, address, hNumber, city, cap, prv, lat, lon;
    Bundle mBun;
    private String mID;
    boolean mSelector = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        mBun = getIntent().getExtras();

        name = (EditText) findViewById(R.id.edit_name);
        surname = (EditText) findViewById(R.id.edit_surname);
        date = (EditText) findViewById(R.id.edit_date);
        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_phone);
        address = (EditText) findViewById(R.id.edit_address);
        hNumber = (EditText) findViewById(R.id.edit_home_number);
        city = (EditText) findViewById(R.id.edit_city);
        cap = (EditText) findViewById(R.id.edit_cap);
        prv = (EditText) findViewById(R.id.edit_province);
        lat = (EditText) findViewById(R.id.edit_Lat);
        lon = (EditText) findViewById(R.id.edit_Long);

        if (mBun != null) {
            mID = "" + mBun.getLong(PersonHelper._ID);
            name.setText(mBun.getString(PersonHelper.NAME));
            surname.setText(mBun.getString(PersonHelper.SURNAME));
            date.setText(mBun.getString(PersonHelper.BORN_DATE));
            email.setText(mBun.getString(PersonHelper.EMAIL));
            phone.setText(mBun.getString(PersonHelper.PHONE));
            address.setText(mBun.getString(PersonHelper.ADDRESS));
            hNumber.setText(mBun.getString(PersonHelper.HOUSE_NUMBER));
            city.setText(mBun.getString(PersonHelper.CITY));
            cap.setText(mBun.getString(PersonHelper.CAP));
            prv.setText(mBun.getString(PersonHelper.PROVINCE));
            lat.setText(mBun.getString(PersonHelper.LAT));
            lon.setText(mBun.getString(PersonHelper.LONG));
            mSelector = false;
        }

        Button btn = (Button) findViewById(R.id.btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPerson();
            }
        });

    }

    void insertPerson() {
        ContentValues values = new ContentValues();
        values.put(PersonHelper.NAME, name.getText().toString());
        values.put(PersonHelper.SURNAME, surname.getText().toString());
        values.put(PersonHelper.BORN_DATE, date.getText().toString());
        values.put(PersonHelper.EMAIL, email.getText().toString());
        values.put(PersonHelper.PHONE, phone.getText().toString());
        values.put(PersonHelper.ADDRESS, address.getText().toString());
        values.put(PersonHelper.HOUSE_NUMBER, hNumber.getText().toString());
        values.put(PersonHelper.CITY, city.getText().toString());
        values.put(PersonHelper.CAP, cap.getText().toString());
        values.put(PersonHelper.PROVINCE, prv.getText().toString());
        values.put(PersonHelper.LAT, lat.getText().toString());
        values.put(PersonHelper.LONG, lon.getText().toString());

        if (mSelector == true) {
            getContentResolver().insert(PersonContentProvider.PERSON_URI, values);
        } else {
            getContentResolver().update(PersonContentProvider.PERSON_URI, values, mID, null);
        }

        startActivity(new Intent(this, PersonDetail.class));

    }
}
