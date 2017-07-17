package com.gabrysuerz.suerzgabriele.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class FoodHelper implements BaseColumns {

    public final static String TABLE_NAME = "foods";
    public final static String NAME_FOOD = "name";
    public final static String QUANTITY_FOOD = "quantity";
    public final static String PRICE_FOOD = "price";
    public final static String FOREIGN_KEY = "foreign_key";

    public final static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            NAME_FOOD + " TEXT NOT NULL, " +
            QUANTITY_FOOD + " INTEGER NOT NULL, " +
            PRICE_FOOD + " INTEGER NOT NULL, " +
            FOREIGN_KEY + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + FOREIGN_KEY + ") REFERENCES " + OrderHelper.TABLE_NAME + "(" + OrderHelper._ID + ")" +
            ");";

    public final static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
