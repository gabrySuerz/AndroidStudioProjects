package com.esame.suerzgabriele_13_06_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class TrainingHelper implements BaseColumns {

    public final static String TABLE_NAME = "training";
    public final static String CITY = "city";
    public final static String TIME = "time";
    public final static String DATE = "date";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            CITY + " TEXT NOT NULL, " +
            DATE + " TEXT NOT NULL, " +
            TIME + " TEXT NOT NULL);";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public final static String COUNT_TRAININGS = "SELECT * FROM " + TABLE_NAME + ";";

}
