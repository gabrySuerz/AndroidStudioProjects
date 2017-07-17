package com.gabrysuerz.esame_2015.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME = "Training.db";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrainingHelper.CREATE_TABLE_QUERY);
        db.execSQL(LapHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TrainingHelper.DROP_TABLE_QUERY);
        db.execSQL(LapHelper.DROP_TABLE_QUERY);
        db.execSQL(TrainingHelper.CREATE_TABLE_QUERY);
        db.execSQL(LapHelper.CREATE_TABLE_QUERY);
    }
}
