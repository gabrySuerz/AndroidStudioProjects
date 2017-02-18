package com.example.gabry.sqldatabase_25_11_2016;

import android.provider.BaseColumns;

/**
 * Created by gabry on 25/11/2016.
 */

//Usare questa classe e le costanti per le query e nel codice

public class ItemsHelper implements BaseColumns {

    public static final String TABLE_NAME = "items";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";

    public static final String CREATE_QUERY =
            "CREATE TABLE " +
                    TABLE_NAME + " ( " +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL, " +
                    QUANTITY + " INTEGER NOT NULL );";

    public static final String DROP_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
