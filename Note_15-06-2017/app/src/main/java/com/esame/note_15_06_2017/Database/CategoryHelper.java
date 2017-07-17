package com.esame.note_15_06_2017.Database;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 22/06/17.
 */

public class CategoryHelper implements BaseColumns {

    public static final String TABLE_NAME = "category_table";
    public static final String CATEGORYNAME = "category_name";
    public static final String COUNT = "count";

    public static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            CATEGORYNAME + " TEXT NOT NULL, " +
            COUNT + " INTEGER NOT NULL" +
            ");";

    public static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
