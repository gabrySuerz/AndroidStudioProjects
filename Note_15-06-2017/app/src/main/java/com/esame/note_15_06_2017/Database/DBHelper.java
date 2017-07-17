package com.esame.note_15_06_2017.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 22/06/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String NAME = "Note.db";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteHelper.CREATE_TABLE_QUERY);
        db.execSQL(CategoryHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NoteHelper.DROP_TABLE_QUERY);
        db.execSQL(CategoryHelper.DROP_TABLE_QUERY);
    }
}
