package com.example.gabry.sqldatabase_25_11_2016;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    DbHelper vHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vHelper = new DbHelper(this);
        insertData();

        readData();
    }

    private void insertData() {
        SQLiteDatabase db = vHelper.getWritableDatabase();
        for (int vInd = 0; vInd < 100; vInd++) {
            ContentValues values = new ContentValues();

            values.put(ItemsHelper.NAME, "nome " + vInd);
            values.put(ItemsHelper.QUANTITY, vInd);

            db.insert(ItemsHelper.TABLE_NAME, null, values);
        }
        db.close();
    }

    private void readData(){
        SQLiteDatabase vDb = vHelper.getReadableDatabase();

        //non usata, si usa query
        //Cursor vCursor = vDb.rawQuery("SELECT * FROM " + ItemsHelper.TABLE_NAME, null);
        Cursor vCursor = vDb.query(ItemsHelper.TABLE_NAME, null, null, null, null, null, null);

        while (vCursor.moveToNext()) {
            int vID = vCursor.getInt(vCursor.getColumnIndex(ItemsHelper._ID));
            String vName = vCursor.getString(vCursor.getColumnIndex(ItemsHelper.NAME));
            int vQuantity = vCursor.getInt(vCursor.getColumnIndex(ItemsHelper.QUANTITY));
            Log.d("TAG", "Row " + vID + ": " + vName + " " + vQuantity);
        }
        vCursor.close();
        vDb.close();
    }

}
