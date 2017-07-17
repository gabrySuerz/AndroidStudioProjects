package com.gabrysuerz.suerzgabriele.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class OrderContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.gabrysuerz.suerzgabriele";
    private static final String ORDER_BASE_PATH = "training";
    private static final String FOOD_BASE_PATH = "laps";
    public static final Uri ORDER_URI = Uri.parse("content://" + AUTHORITY + "/" + ORDER_BASE_PATH);
    public static final Uri FOOD_URI = Uri.parse("content://" + AUTHORITY + "/" + FOOD_BASE_PATH);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ORDERS = 1;
    private static final int ORDER = 2;
    private static final int FOODS = 3;
    private static final int FOOD = 4;

    static {
        sUriMatcher.addURI(AUTHORITY, ORDER_BASE_PATH, ORDERS);
        sUriMatcher.addURI(AUTHORITY, ORDER_BASE_PATH + "/#", ORDER);
        sUriMatcher.addURI(AUTHORITY, FOOD_BASE_PATH, FOODS);
        sUriMatcher.addURI(AUTHORITY, FOOD_BASE_PATH + "/#", FOOD);
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
            case ORDERS:
                vQueryBuilder.setTables(OrderHelper.TABLE_NAME);
                break;
            case ORDER:
                vQueryBuilder.setTables(OrderHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(OrderHelper._ID + "=" + uri.getLastPathSegment());
                break;
            case FOODS:
                vQueryBuilder.setTables(FoodHelper.TABLE_NAME);
                break;
            case FOOD:
                vQueryBuilder.setTables(FoodHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(FoodHelper.FOREIGN_KEY + "=" + uri.getLastPathSegment());
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
            case ORDERS:
                vID = vDB.insert(OrderHelper.TABLE_NAME, null, values);
                vPath = ORDER_BASE_PATH;
                break;
            case FOODS:
                vID = vDB.insert(FoodHelper.TABLE_NAME, null, values);
                vPath = FOOD_BASE_PATH;
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
            case ORDERS:
                vD = vDB.delete(OrderHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case ORDER:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(OrderHelper.TABLE_NAME, OrderHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(OrderHelper.TABLE_NAME, OrderHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;
            case FOODS:
                vD = vDB.delete(FoodHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case FOOD:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(FoodHelper.TABLE_NAME, FoodHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(FoodHelper.TABLE_NAME, FoodHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
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
            case ORDER:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(OrderHelper.TABLE_NAME, values, OrderHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(OrderHelper.TABLE_NAME, values, OrderHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            case FOOD:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(FoodHelper.TABLE_NAME, values, FoodHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(FoodHelper.TABLE_NAME, values, FoodHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vU;
    }
}
