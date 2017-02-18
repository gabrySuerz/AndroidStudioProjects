package com.gabrysuerz.realm_test_16_02_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;

public class DetailsActivity extends AppCompatActivity {

    TextView name, surname, date, email, phone, address, hNumber, city, cap, prv, lat, lon;
    public final static String ID = "bun_id";
    long mID;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null)
            mID = getIntent().getExtras().getLong(ID);

        name = (TextView) findViewById(R.id.txt_name);
        surname = (TextView) findViewById(R.id.txt_surname);
        name.setText(realm.where(Person.class).equalTo("ID", mID).findAll().first().getName());
    }
}
