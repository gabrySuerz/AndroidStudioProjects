package com.example.gabry.tantodevocancellarlo.Database;

import android.provider.BaseColumns;

/**
 * Created by gabry on 13/12/2016.
 */

public class ItemHelper implements BaseColumns {

    public static final String TABLE_NAME = "newTable";
    public static final String FIRST = "firstField";
    public static final String SECOND = "secondField";

    public static final String CREATE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FIRST + " TEXT NOT NULL, " +
            SECOND + " TEXT NOT NULL);";
}
