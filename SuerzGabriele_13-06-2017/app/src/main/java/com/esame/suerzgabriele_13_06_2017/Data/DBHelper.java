package com.esame.suerzgabriele_13_06_2017.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String NAME = "Training.db";
    public static int VERSION = 7;

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrainingHelper.CREATE_TABLE_QUERY);
        db.execSQL(TrainingDetailHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TrainingHelper.DROP_TABLE_QUERY);
        db.execSQL(TrainingDetailHelper.DROP_TABLE_QUERY);
        db.execSQL(TrainingHelper.CREATE_TABLE_QUERY);
        db.execSQL(TrainingDetailHelper.CREATE_TABLE_QUERY);
    }
}
