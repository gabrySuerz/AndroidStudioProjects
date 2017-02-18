package com.gabrysuerz.anagrafiche_15_02_2017;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.content.ContentProvider;

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
            mID = "" + mBun.getLong(MainActivity.ID);
            name.setText(mBun.getString(Item.NAME));
            surname.setText(mBun.getString(Item.SURNAME));
            date.setText(mBun.getString(Item.BORN_DATE));
            email.setText(mBun.getString(Item.EMAIL));
            phone.setText(mBun.getString(Item.PHONE));
            address.setText(mBun.getString(Item.ADDRESS));
            hNumber.setText(mBun.getString(Item.HOUSE_NUMBER));
            city.setText(mBun.getString(Item.CITY));
            cap.setText(mBun.getString(Item.CAP));
            prv.setText(mBun.getString(Item.PROVINCE));
            lat.setText(mBun.getString(Item.LAT));
            lon.setText(mBun.getString(Item.LONG));
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
        values.put(Item.NAME, name.getText().toString());
        values.put(Item.SURNAME, surname.getText().toString());
        values.put(Item.BORN_DATE, date.getText().toString());
        values.put(Item.EMAIL, email.getText().toString());
        values.put(Item.PHONE, phone.getText().toString());
        values.put(Item.ADDRESS, address.getText().toString());
        values.put(Item.HOUSE_NUMBER, hNumber.getText().toString());
        values.put(Item.CITY, city.getText().toString());
        values.put(Item.CAP, cap.getText().toString());
        values.put(Item.PROVINCE, prv.getText().toString());
        values.put(Item.LAT, lat.getText().toString());
        values.put(Item.LONG, lon.getText().toString());

        getContentResolver().insert(ContentProvider.createUri(Item.class, null), values);

        startActivity(new Intent(this, PersonDetail.class));

    }
}
