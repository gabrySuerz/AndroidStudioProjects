package com.example.gabry.datasource_02_12_2016.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by gabry on 02/12/2016.
 */
public class Dataset {

    private static Dataset mInstance;

    public static Dataset Get(Context aContext) {
        if (mInstance == null)
            mInstance = new Dataset(aContext);
        return mInstance;
    }

    ArrayList<Contact> mContacts;
    DbHelper mDbHelper;

    private Dataset(Context aContext) {
        mContacts = new ArrayList<>();
        mDbHelper = new DbHelper(aContext);
    }

    public ArrayList<Contact> getContacts() {
        SQLiteDatabase vDB = mDbHelper.getReadableDatabase();
        Cursor vCursor = vDB.query(ContactsHelper.TABLE_NAME, null, null, null, null, null, null);
        while (vCursor.moveToNext()) {
            Contact vContact = new Contact();
            vContact.mID = vCursor.getLong(vCursor.getColumnIndex(ContactsHelper._ID));
            vContact.mName = vCursor.getString(vCursor.getColumnIndex(ContactsHelper.NAME));
            vContact.mSurname = vCursor.getString(vCursor.getColumnIndex(ContactsHelper.SURNAME));
            mContacts.add(vContact);
        }
        vCursor.close();
        vDB.close();
        return mContacts;
    }

    public Contact addContact(Contact aContact) {
        SQLiteDatabase vDB = mDbHelper.getWritableDatabase();

        ContentValues vValues = new ContentValues();
        vValues.put(ContactsHelper.NAME, aContact.mName);
        vValues.put(ContactsHelper.SURNAME, aContact.mSurname);

        long vIDIns = vDB.insert(ContactsHelper.TABLE_NAME, null, vValues);

        aContact.mID = vIDIns;
        mContacts.add(aContact);

        vDB.close();
        return aContact;
    }

    public boolean removeContact(long aID) {
        SQLiteDatabase vDB = mDbHelper.getWritableDatabase();
        int vDel = vDB.delete(ContactsHelper.TABLE_NAME, ContactsHelper._ID + " = " + aID, null);
        int vRemove = -1;

        for (int i = 0; i < mContacts.size(); i++) {
            if (mContacts.get(i).mID == aID) {
                vRemove = i;
                break;
            }
        }

        if (vRemove >= 0)
            mContacts.remove(vRemove);
        return vDel > 0;
    }
}
