package com.example.gabrysuerz.es_13_01_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 13/01/17.
 */

public class TempHelper implements BaseColumns {
    public final static String TABLE_NAME_TWO = "temp_table";
    public final static String TEMP_CITIES = "Temperatura";
    public final static String TEMP_ID = "Citta";
    public final static String TEMP_DATE = "Data";

    public final static String CREATE_QUERY_TEMP = "CREATE TABLE " +
            TABLE_NAME_TWO + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TEMP_CITIES + " INTEGER NOT NULL, " +
            TEMP_DATE + " TEXT NOT NULL, " +
            TEMP_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + TEMP_ID + ") REFERENCES " + CitiesHelper.TABLE_NAME_ONE + "(" + CitiesHelper._ID + ")" +
            ");";

    public final static String DROP_QUERY_TEMP = "DROP TABLE IF EXISTS " + TABLE_NAME_TWO + ";";
}
