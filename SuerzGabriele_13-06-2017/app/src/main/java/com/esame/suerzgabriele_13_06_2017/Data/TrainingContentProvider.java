package com.esame.suerzgabriele_13_06_2017.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class TrainingContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.esame.suerzgabriele_13_06_2017";
    private static final String TRAINING_BASE_PATH = "training";
    private static final String DETAIL_BASE_PATH = "detail";
    public static final Uri TRAINING_URI = Uri.parse("content://" + AUTHORITY + "/" + TRAINING_BASE_PATH);
    public static final Uri DETAIL_URI = Uri.parse("content://" + AUTHORITY + "/" + DETAIL_BASE_PATH);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_TRAINING1 = 1;
    private static final int CODE_TRAINING2 = 2;
    private static final int CODE_DETAIL1 = 3;
    private static final int CODE_DETAIL2 = 4;

    static {
        sUriMatcher.addURI(AUTHORITY, TRAINING_BASE_PATH, CODE_TRAINING1);
        sUriMatcher.addURI(AUTHORITY, TRAINING_BASE_PATH + "/#", CODE_TRAINING2);
        sUriMatcher.addURI(AUTHORITY, DETAIL_BASE_PATH, CODE_DETAIL1);
        sUriMatcher.addURI(AUTHORITY, DETAIL_BASE_PATH + "/#", CODE_DETAIL2);
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
            case CODE_TRAINING1:
                vQueryBuilder.setTables(TrainingHelper.TABLE_NAME);
                break;
            case CODE_TRAINING2:
                vQueryBuilder.setTables(TrainingHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(TrainingHelper._ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_DETAIL1:
                vQueryBuilder.setTables(TrainingDetailHelper.TABLE_NAME);
                break;
            case CODE_DETAIL2:
                vQueryBuilder.setTables(TrainingDetailHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(TrainingDetailHelper.SESSION + "=" + uri.getLastPathSegment());
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
            case CODE_TRAINING1:
                vID = vDB.insert(TrainingHelper.TABLE_NAME, null, values);
                vPath = TRAINING_BASE_PATH;
                break;
            case CODE_DETAIL1:
                vID = vDB.insert(TrainingDetailHelper.TABLE_NAME, null, values);
                vPath = DETAIL_BASE_PATH;
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
            case CODE_TRAINING1:
                vD = vDB.delete(TrainingHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_TRAINING2:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(TrainingHelper.TABLE_NAME, TrainingHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(TrainingHelper.TABLE_NAME, TrainingHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;

            case CODE_DETAIL1:
                vD = vDB.delete(TrainingDetailHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_DETAIL2:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(TrainingDetailHelper.TABLE_NAME, TrainingDetailHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(TrainingDetailHelper.TABLE_NAME, TrainingDetailHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
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
            case CODE_TRAINING2:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(TrainingHelper.TABLE_NAME, values, TrainingHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(TrainingHelper.TABLE_NAME, values, TrainingHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            case CODE_DETAIL2:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(TrainingDetailHelper.TABLE_NAME, values, TrainingDetailHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(TrainingDetailHelper.TABLE_NAME, values, TrainingDetailHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vU;
    }

}

