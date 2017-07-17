package com.gabrysuerz.suerzgabriele.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabrysuerz on 17/07/17.
 */

class DBHelper extends SQLiteOpenHelper {
    private static final String NAME = "Order.db";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase aDB) {
        aDB.execSQL(OrderHelper.CREATE_TABLE_QUERY);
        aDB.execSQL(FoodHelper.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase aDB, int i, int i1) {
        aDB.execSQL(OrderHelper.DROP_TABLE_QUERY);
        aDB.execSQL(OrderHelper.CREATE_TABLE_QUERY);
        aDB.execSQL(FoodHelper.DROP_TABLE_QUERY);
        aDB.execSQL(FoodHelper.CREATE_TABLE_QUERY);
    }
}
