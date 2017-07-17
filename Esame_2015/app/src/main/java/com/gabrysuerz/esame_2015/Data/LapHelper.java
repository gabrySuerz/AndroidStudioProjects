package com.gabrysuerz.esame_2015.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class LapHelper implements BaseColumns {

    public final static String TABLE_NAME = "laps";
    public final static String NUMBER = "n_lap";
    public final static String TIME = "time_lap";
    public final static String SESSION = "foreign_key";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            NUMBER + " INTEGER NOT NULL, " +
            TIME + " INTEGER NOT NULL, " +
            SESSION + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + SESSION + ") REFERENCES " + TrainingHelper.TABLE_NAME + "(" + TrainingHelper._ID + ")" +
            ");";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public final static String COUNT_LAPS(long aForeign) {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + SESSION + " = " + aForeign + ";";
    }
}
