package com.gabrysuerz.es_20_02_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class Helper implements BaseColumns {

    public static final String TABLE_NAME = "table_n1";
    public static final String COLUMN1 = "colonna1";
    public static final String COLUMN2 = "colonna2";


    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + "INTEGER NOT NULL PRIMARY KEY" +
            COLUMN1 + "TEXT NOT NULL" +
            COLUMN2 + "TEXT NOT NULL" + ");";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
