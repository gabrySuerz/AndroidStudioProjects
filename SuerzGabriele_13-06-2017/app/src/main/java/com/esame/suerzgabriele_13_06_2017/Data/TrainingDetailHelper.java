package com.esame.suerzgabriele_13_06_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class TrainingDetailHelper implements BaseColumns {

    public final static String TABLE_NAME = "laps";
    public final static String TIME = "time";
    public final static String N_LAPS = "n_laps";
    public final static String SESSION = "foreign_key";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            N_LAPS + " INTEGER NOT NULL, " +
            TIME + " STRING NOT NULL, " +
            SESSION + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + SESSION + ") REFERENCES " + TrainingHelper.TABLE_NAME + "(" + TrainingHelper._ID + ")" +
            ");";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public final static String laps(long training_session) {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + SESSION + "=" + training_session + ";";
    }
}
