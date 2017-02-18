package com.example.gabry.contentprovider_16_12_2016.Data;

import android.provider.BaseColumns;

/**
 * Created by gabry on 16/12/2016.
 */

public class ContactsHelper implements BaseColumns {
    public static final String TABLE_NAME = "contacts";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    public static final String CREATE_QUERY =
            "CREATE TABLE " +
                    TABLE_NAME + " ( " +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SURNAME + " TEXT NOT NULL, " +
                    NAME + " TEXT NOT NULL );";

    public static final String DROP_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
