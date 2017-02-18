package com.example.gabry.sqldatabase_25_11_2016;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabry on 25/11/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MioDatabase";
    private static final int VERSION = 1;

    public DbHelper(Context aContext) {
        super(aContext, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Solo se il database non esiste
        db.execSQL(ItemsHelper.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ItemsHelper.DROP_QUERY);
    }
}
