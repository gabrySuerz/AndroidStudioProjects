package com.esame.suerzgabriele.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String NAME = "Pizzeria.db";
    public static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReservationHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ReservationHelper.DROP_TABLE_QUERY);
    }
}
