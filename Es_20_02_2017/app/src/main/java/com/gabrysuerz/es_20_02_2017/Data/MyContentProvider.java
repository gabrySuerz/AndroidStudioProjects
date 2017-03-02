package com.gabrysuerz.es_20_02_2017.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class MyContentProvider extends ContentProvider {

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE;

    private static final String AUTHORITY = "";
    private static final String BASE_PATH = "";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE1 = 1;
    private static final int CODE2 = 2;

    // da aggiungere al manifest
    //<provider android:authorities="com.gabrysuerz.es_20_02_2017" android:name=".Data.MyContentProvider"></provider>

    static {
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, CODE1);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, CODE2);
    }

    DbHelper DbHelper;

    @Override
    public boolean onCreate() {
        DbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder vQueryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase vDB = DbHelper.getReadableDatabase();
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case CODE1:
                vQueryBuilder.setTables(Helper.TABLE_NAME);
                break;
            case CODE2:
                vQueryBuilder.setTables(Helper.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Cursor vCursor = vQueryBuilder.query(vDB, projection, selection, selectionArgs, null, null, sortOrder);
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
        SQLiteDatabase vDB = DbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        long vID = -1;
        String vPath;
        switch (vUriType) {
            case CODE1:
                vID = vDB.insert(Helper.TABLE_NAME, null, values);
                vPath = BASE_PATH;
                break;
            case CODE2:
                vID = vDB.insert(Helper.TABLE_NAME, null, values);
                vPath = BASE_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        if (vID >= 0)
            return Uri.parse("content://" + AUTHORITY + "/" + vPath + "/" + vID);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = DbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vNum;
        switch (vUriType) {
            case CODE1:
                vNum = vDB.delete(Helper.TABLE_NAME, uri.getLastPathSegment() + "=" + Helper._ID, null);
                break;
            case CODE2:
                vNum = vDB.delete(Helper.TABLE_NAME, uri.getLastPathSegment() + "=" + Helper._ID, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vNum;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = DbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vNum;
        switch (vUriType) {
            case CODE1:
                vNum = vDB.update(Helper.TABLE_NAME, values, uri.getLastPathSegment() + "=" + Helper._ID, selectionArgs);
                break;
            case CODE2:
                vNum = vDB.update(Helper.TABLE_NAME, values, uri.getLastPathSegment() + "=" + Helper._ID, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vNum;
    }
}
