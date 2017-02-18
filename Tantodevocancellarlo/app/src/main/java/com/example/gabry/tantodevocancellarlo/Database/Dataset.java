package com.example.gabry.tantodevocancellarlo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by gabry on 13/12/2016.
 */

public class Dataset {

    private static Dataset mInstance;

    public static Dataset Get(Context aContext) {
        if (mInstance == null)
            mInstance = new Dataset(aContext);
        return mInstance;
    }

    DBHelper dbH;
    ArrayList<Item> arrayItems;

    public Dataset(Context aContext) {
        dbH = new DBHelper(aContext);
        arrayItems = new ArrayList<>();
    }

    public Item addItem(Item aItem) {
        SQLiteDatabase vDB = dbH.getWritableDatabase();
        ContentValues vValue = new ContentValues();
        vValue.put(ItemHelper.FIRST, aItem.first);
        vValue.put(ItemHelper.SECOND, aItem.second);
        long vNID = vDB.insert(ItemHelper.TABLE_NAME, null, vValue);
        vDB.close();
        aItem.id = vNID;
        arrayItems.add(aItem);
        return aItem;
    }

    public ArrayList<Item> getItems() {
        SQLiteDatabase vDB = dbH.getReadableDatabase();
        Cursor vCursor = vDB.query(ItemHelper.TABLE_NAME, null, null, null, null, null, null);
        while (vCursor.moveToNext()) {
            Item vItem = new Item();
            vItem.id = vCursor.getLong(vCursor.getColumnIndex(ItemHelper._ID));
            vItem.first = vCursor.getString(vCursor.getColumnIndex(ItemHelper.FIRST));
            vItem.second = vCursor.getString(vCursor.getColumnIndex(ItemHelper.SECOND));
            arrayItems.add(vItem);
        }
        vCursor.close();
        vDB.close();
        return arrayItems;
    }

    public Item modifyItem(Item aItem) {
        SQLiteDatabase vDB = dbH.getWritableDatabase();
        ContentValues vValue = new ContentValues();
        vValue.put(ItemHelper._ID, aItem.id);
        vValue.put(ItemHelper.FIRST, aItem.first);
        vValue.put(ItemHelper.SECOND, aItem.second);
        long vNID = vDB.update(ItemHelper.TABLE_NAME, vValue, ItemHelper._ID + "=" + aItem.id, null);
        vDB.close();
        aItem.id = vNID;
        arrayItems.add(aItem);
        return aItem;
    }

    public boolean deleteItem(long aID) {
        SQLiteDatabase vDB = dbH.getWritableDatabase();
        int vDel = vDB.delete(ItemHelper.TABLE_NAME, ItemHelper._ID + "=" + aID, null);
        int vRem = -1;
        vDB.close();

        for (int vI = 0; vI < arrayItems.size(); vI++) {
            if (arrayItems.get(vI).id == aID) {
                vRem = vI;
                break;
            }
        }

        if (vRem > 0) {
            arrayItems.remove(vRem);
        }
        return vDel > 0;
    }
}
