package com.gabrysuerz.suerzgabriele.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class OrderHelper implements BaseColumns {

    public final static String TABLE_NAME = "orders";
    public final static String DATE = "date";
    public final static String QUANTITY = "quantity";
    public final static String PRICE = "import";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            DATE + " INTEGER NOT NULL, " +
            QUANTITY + " INTEGER NOT NULL, " +
            PRICE + " INTEGER NOT NULL);";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
