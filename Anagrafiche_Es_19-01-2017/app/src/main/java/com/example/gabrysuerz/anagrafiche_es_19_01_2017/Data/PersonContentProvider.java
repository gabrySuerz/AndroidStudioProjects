package com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by gabrysuerz on 19/01/17.
 */

public class PersonContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.gabrysuerz.anagrafiche_es_19_01_2017";
    private static final String PERSON_BASE_PATH = "person";
    public static final Uri PERSON_URI = Uri.parse("content://" + AUTHORITY + "/" + PERSON_BASE_PATH);

    private static final int PERSON = 10;
    private static final int PERSON_ID = 20;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, PERSON_BASE_PATH, PERSON);
        sUriMatcher.addURI(AUTHORITY, PERSON_BASE_PATH + "/#", PERSON_ID);
    }

    DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder vQueryBuilder = new SQLiteQueryBuilder();
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case PERSON:
                vQueryBuilder.setTables(PersonHelper.TABLE_NAME_ONE);
                break;
            case PERSON_ID:
                vQueryBuilder.setTables(PersonHelper.TABLE_NAME_ONE);
                vQueryBuilder.appendWhere(PersonHelper._ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        Cursor vCursor = vQueryBuilder.query(vDB, projection, selection, selectionArgs, null, null, sortOrder);
        vCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return vCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        long vID = -1;

        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case PERSON:
                vID = vDB.insert(PersonHelper.TABLE_NAME_ONE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        if (vID >= 0) {
            return Uri.parse(PERSON_URI + "/" + vID);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        int vID = 0;
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case PERSON:
                vID = vDB.delete(PersonHelper.TABLE_NAME_ONE, null, null);
                break;
            case PERSON_ID:
                vID = vDB.delete(PersonHelper.TABLE_NAME_ONE, PersonHelper._ID + "=" + uri.getLastPathSegment(), null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vID;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        int vID = 0;
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case PERSON_ID:
                vID = vDB.update(PersonHelper.TABLE_NAME_ONE, values, PersonHelper._ID + "=" + uri.getLastPathSegment(), null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vID;
    }
}
