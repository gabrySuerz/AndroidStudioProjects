package com.example.gabry.contentprovider_16_12_2016.Data;

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
 * Created by gabry on 16/12/2016.
 */

public class ContactContentProvider extends ContentProvider {

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/contacts";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/contacts";

    private static final String AUTHORITY = "com.example.gabry.contentprovider_16_12_2016";
    private static final String CONTACTS_BASE_PATH = "contact";
    public static final Uri CONTACTS_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_BASE_PATH);

    private static final int CONTACTS = 10;
    private static final int CONTACTS_ID = 20;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, CONTACTS_BASE_PATH, CONTACTS);
        sUriMatcher.addURI(AUTHORITY, CONTACTS_BASE_PATH + "/#", CONTACTS_ID);
    }

    private DbHelper dbHelper;

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
            case CONTACTS:
                vQueryBuilder.setTables(ContactsHelper.TABLE_NAME);
                break;
            case CONTACTS_ID:
                vQueryBuilder.setTables(ContactsHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(ContactsHelper._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase vDb = dbHelper.getReadableDatabase();
        Cursor vCursor = vQueryBuilder.query(vDb, projection, selection, selectionArgs, null, null, sortOrder);
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
        int vUriType = sUriMatcher.match(uri);
        SQLiteDatabase vDb = dbHelper.getWritableDatabase();
        long vId = 0;
        switch (vUriType) {
            case CONTACTS:
                vId = vDb.insert(ContactsHelper.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        if (vId > 0) {
            return Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_BASE_PATH + "/" + vId);
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int vUriType = sUriMatcher.match(uri);
        SQLiteDatabase vDb = dbHelper.getWritableDatabase();
        int vDeletedRows = 0;
        switch (vUriType) {
            case CONTACTS:
                vDeletedRows = vDb.delete(ContactsHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                if (TextUtils.isEmpty(selection))
                    vDeletedRows = vDb.delete(ContactsHelper.TABLE_NAME, uri.getLastPathSegment() + " = " + ContactsHelper._ID, null);
                else
                    vDeletedRows = vDb.delete(ContactsHelper.TABLE_NAME, uri.getLastPathSegment() + " = " +
                            ContactsHelper._ID + " and " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vDeletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int vUriType = sUriMatcher.match(uri);
        SQLiteDatabase vDb = dbHelper.getWritableDatabase();
        int vUpdatedRows = 0;
        switch (vUriType) {
            case CONTACTS:
                vUpdatedRows = vDb.update(ContactsHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                if (TextUtils.isEmpty(selection))
                    vUpdatedRows = vDb.update(ContactsHelper.TABLE_NAME, values, uri.getLastPathSegment() + " = " + ContactsHelper._ID, null);
                else
                    vUpdatedRows = vDb.update(ContactsHelper.TABLE_NAME, values, uri.getLastPathSegment() + " = " +
                            ContactsHelper._ID + " and " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        vDb.close();
        return vUpdatedRows;
    }
}
