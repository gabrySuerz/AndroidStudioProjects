package com.example.gabrysuerz.es_13_01_2017.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by gabrysuerz on 13/01/17.
 */

public class CitiesContentProvider extends ContentProvider {

    /*public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/cities";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/cities";*/

    private static final String AUTHORITY = "com.example.gabrysuerz.es_13_01_2017";
    private static final String CITIES_BASE_PATH = "cities";
    public static final Uri CITIES_URI = Uri.parse("content://" + AUTHORITY + "/" + CITIES_BASE_PATH);
    private static final String CITY_BASE_PATH = "detail";
    public static final Uri CITY_DETAIL = Uri.parse("content://" + AUTHORITY + "/" + CITY_BASE_PATH);

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CITIES = 10;
    private static final int CITIES_ID = 20;
    private static final int CITY = 30;
    private static final int CITY_ID = 40;

    static {
        sUriMatcher.addURI(AUTHORITY, CITIES_BASE_PATH, CITIES);
        sUriMatcher.addURI(AUTHORITY, CITIES_BASE_PATH + "/#", CITIES_ID);
        sUriMatcher.addURI(AUTHORITY, CITY_BASE_PATH, CITY);
        sUriMatcher.addURI(AUTHORITY, CITY_BASE_PATH + "/#", CITY_ID);
    }

    public DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder vQueryBuilder = new SQLiteQueryBuilder();
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case CITIES:
                vQueryBuilder.setTables(CitiesHelper.TABLE_NAME_ONE);
                break;
            case CITIES_ID:
                vQueryBuilder.setTables(CitiesHelper.TABLE_NAME_ONE);
                vQueryBuilder.appendWhere(CitiesHelper._ID + "=" + uri.getLastPathSegment());
                break;
            case CITY:
                vQueryBuilder.setTables(TempHelper.TABLE_NAME_TWO);
                break;
            case CITY_ID:
                vQueryBuilder.setTables(TempHelper.TABLE_NAME_TWO);
                vQueryBuilder.appendWhere(TempHelper.TEMP_ID + "=" + uri.getLastPathSegment());
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
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        long vID = -1;
        String vPath;
        switch (vUriType) {
            case CITIES:
                vID = vDB.insert(CitiesHelper.TABLE_NAME_ONE, null, values);
                vPath = CITIES_BASE_PATH;
                break;
            case CITY:
                vID = vDB.insert(TempHelper.TABLE_NAME_TWO, null, values);
                vPath = CITY_BASE_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        if (vID >= 0) {
            return Uri.parse("content://" + AUTHORITY + "/" + vPath + "/" + vID);
        } else
            return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vD = 0;
        switch (vUriType) {
            case CITIES:
                vD = vDB.delete(CitiesHelper.TABLE_NAME_ONE, selection, selectionArgs);
                break;
            case CITIES_ID:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(CitiesHelper.TABLE_NAME_ONE, CitiesHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(CitiesHelper.TABLE_NAME_ONE, CitiesHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;

            case CITY:
                vD = vDB.delete(TempHelper.TABLE_NAME_TWO, selection, selectionArgs);
                break;
            case CITY_ID:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(TempHelper.TABLE_NAME_TWO, TempHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(TempHelper.TABLE_NAME_TWO, TempHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vD;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vU = 0;
        switch (vUriType) {
            case CITIES_ID:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(CitiesHelper.TABLE_NAME_ONE, values, CitiesHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(CitiesHelper.TABLE_NAME_ONE, values, CitiesHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            case CITY_ID:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(TempHelper.TABLE_NAME_TWO, values, TempHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(TempHelper.TABLE_NAME_TWO, values, TempHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vU;
    }

}
