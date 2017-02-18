package com.example.gabrysuerz.es_13_01_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 13/01/17.
 */
public class CitiesHelper implements BaseColumns {

    public final static String TABLE_NAME_ONE = "cities_table";
    public final static String NAME_CITIES = "Citta";

    public final static String CREATE_QUERY_CITIES = "CREATE TABLE " +
            TABLE_NAME_ONE + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME_CITIES + " TEXT NOT NULL);";

    public final static String DROP_QUERY_CITIES = "DROP TABLE IF EXISTS " + TABLE_NAME_ONE + ";";

}
