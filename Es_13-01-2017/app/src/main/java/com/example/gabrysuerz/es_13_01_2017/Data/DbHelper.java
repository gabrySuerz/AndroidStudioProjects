package com.example.gabrysuerz.es_13_01_2017.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 13/01/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public final static String NAME = "cities.db";
    public final static int VERSION = 1;

    public DbHelper(Context aContext) {
        super(aContext, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CitiesHelper.CREATE_QUERY_CITIES);
        db.execSQL(TempHelper.CREATE_QUERY_TEMP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CitiesHelper.DROP_QUERY_CITIES);
        db.execSQL(TempHelper.DROP_QUERY_TEMP);
    }
}
