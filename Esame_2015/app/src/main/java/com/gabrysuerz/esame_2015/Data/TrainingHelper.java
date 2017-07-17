package com.gabrysuerz.esame_2015.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 16/07/17.
 */

public class TrainingHelper implements BaseColumns {

    public final static String TABLE_NAME = "trainings";
    public final static String TRAINING = "training";
    public final static String TIME = "time";
    public final static String DATE = "date";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            TRAINING + " TEXT NOT NULL, " +
            DATE + " INTEGER NOT NULL, " +
            TIME + " INTEGER NOT NULL);";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public final static String COUNT_TRAININGS = "SELECT * FROM " + TABLE_NAME + ";";

}
