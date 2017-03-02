package com.gabrysuerz.es_20_02_2017.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String NAME = "Name.db";
    public static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Helper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Helper.DROP_TABLE_QUERY);
    }
}
