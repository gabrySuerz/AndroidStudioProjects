package com.gabrysuerz.anagrafiche_15_02_2017;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PersonDetail extends AppCompatActivity {
/*
    Cursor mCursor;
    TextView name, surname, date, email, phone, address, hNumber, city, cap, prv, lat, lon;
    Toolbar toolbar;
    long mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mID = getIntent().getExtras().getLong(MainActivity.ID);

        //mCursor = getContentResolver().query(Uri.parse(DataActivity.PERSON_URI + "/" + mID), null, null, null, null, null);

        name = (TextView) findViewById(R.id.txt_name);
        surname = (TextView) findViewById(R.id.txt_surname);
        date = (TextView) findViewById(R.id.txt_date);
        email = (TextView) findViewById(R.id.txt_email);
        phone = (TextView) findViewById(R.id.txt_phone);
        address = (TextView) findViewById(R.id.txt_address);
        hNumber = (TextView) findViewById(R.id.txt_home_number);
        city = (TextView) findViewById(R.id.txt_city);
        cap = (TextView) findViewById(R.id.txt_cap);
        prv = (TextView) findViewById(R.id.txt_province);
        lat = (TextView) findViewById(R.id.txt_Lat);
        lon = (TextView) findViewById(R.id.txt_Long);

        setGui();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent vIntent = new Intent(this, PersonDetail.class);
        vIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_modify) {
            modify();
            return true;
        } else if (id == R.id.action_delete) {
            getContentResolver().delete(Uri.parse(DataActivity.PERSON_URI + "/" + mID), null, null);
            startActivity(vIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void modify() {
        Intent vIntent = new Intent(this, AddPersonActivity.class);
        Bundle vBun = new Bundle();
        vBun.putLong(PersonHelper._ID, mCursor.getLong(mCursor.getColumnIndex(PersonHelper._ID)));
        vBun.putString(PersonHelper.NAME, mCursor.getString(mCursor.getColumnIndex(PersonHelper.NAME)));
        vBun.putString(PersonHelper.SURNAME, mCursor.getString(mCursor.getColumnIndex(PersonHelper.SURNAME)));
        vBun.putString(PersonHelper.BORN_DATE, mCursor.getString(mCursor.getColumnIndex(PersonHelper.BORN_DATE)));
        vBun.putString(PersonHelper.EMAIL, mCursor.getString(mCursor.getColumnIndex(PersonHelper.EMAIL)));
        vBun.putString(PersonHelper.PHONE, mCursor.getString(mCursor.getColumnIndex(PersonHelper.PHONE)));
        vBun.putString(PersonHelper.ADDRESS, mCursor.getString(mCursor.getColumnIndex(PersonHelper.ADDRESS)));
        vBun.putString(PersonHelper.HOUSE_NUMBER, mCursor.getString(mCursor.getColumnIndex(PersonHelper.HOUSE_NUMBER)));
        vBun.putString(PersonHelper.CITY, mCursor.getString(mCursor.getColumnIndex(PersonHelper.CITY)));
        vBun.putString(PersonHelper.CAP, mCursor.getString(mCursor.getColumnIndex(PersonHelper.CAP)));
        vBun.putString(PersonHelper.PROVINCE, mCursor.getString(mCursor.getColumnIndex(PersonHelper.PROVINCE)));
        vBun.putString(PersonHelper.LAT, mCursor.getString(mCursor.getColumnIndex(PersonHelper.LAT)));
        vBun.putString(PersonHelper.LONG, mCursor.getString(mCursor.getColumnIndex(PersonHelper.LONG)));
        vIntent.putExtras(vBun);
        startActivity(vIntent);
    }

    private void setGui() {
        mCursor.moveToFirst();
        name.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.NAME)));
        surname.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.SURNAME)));
        date.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.BORN_DATE)));
        email.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.EMAIL)));
        phone.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.PHONE)));
        address.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.ADDRESS)));
        hNumber.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.HOUSE_NUMBER)));
        city.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.CITY)));
        cap.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.CAP)));
        prv.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.PROVINCE)));
        lat.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.LAT)));
        lon.setText(mCursor.getString(mCursor.getColumnIndex(PersonHelper.LONG)));
    }*/
}
