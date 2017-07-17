package com.esame.note_15_06_2017.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class NoteContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.esame.note_15_06_2017";
    private static final String NOTE_BASE_PATH = "note";
    private static final String CATEGORY_BASE_PATH = "detail";
    public static final Uri NOTE_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTE_BASE_PATH);
    public static final Uri CATEGORY_URI = Uri.parse("content://" + AUTHORITY + "/" + CATEGORY_BASE_PATH);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int NOTE_1 = 1;
    private static final int NOTE_2 = 2;
    private static final int CATEGORY_1 = 3;
    private static final int CATEGORY_2 = 4;

    static {
        sUriMatcher.addURI(AUTHORITY, NOTE_BASE_PATH, NOTE_1);
        sUriMatcher.addURI(AUTHORITY, NOTE_BASE_PATH + "/#", NOTE_2);
        sUriMatcher.addURI(AUTHORITY, CATEGORY_BASE_PATH, CATEGORY_1);
        sUriMatcher.addURI(AUTHORITY, CATEGORY_BASE_PATH + "/#", CATEGORY_2);
    }

    DBHelper dbHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vD = 0;
        switch (vUriType) {
            case NOTE_1:
                vD = vDB.delete(NoteHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case NOTE_2:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(NoteHelper.TABLE_NAME, NoteHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(NoteHelper.TABLE_NAME, NoteHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;

            case CATEGORY_1:
                vD = vDB.delete(CategoryHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY_2:
                if (TextUtils.isEmpty(selection)) {
                    vD = vDB.delete(CategoryHelper.TABLE_NAME, CategoryHelper._ID + " = " + uri.getLastPathSegment(), null);
                } else {
                    vD = vDB.delete(CategoryHelper.TABLE_NAME, CategoryHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vD;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        long vID = -1;
        String vPath;
        switch (vUriType) {
            case NOTE_1:
                vID = vDB.insert(NoteHelper.TABLE_NAME, null, values);
                vPath = NOTE_BASE_PATH;
                break;
            case CATEGORY_1:
                vID = vDB.insert(CategoryHelper.TABLE_NAME, null, values);
                vPath = CATEGORY_BASE_PATH;
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
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder vQueryBuilder = new SQLiteQueryBuilder();
        int vUriType = sUriMatcher.match(uri);
        switch (vUriType) {
            case NOTE_1:
                vQueryBuilder.setTables(NoteHelper.TABLE_NAME);
                break;
            case NOTE_2:
                vQueryBuilder.setTables(NoteHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(NoteHelper._ID + "=" + uri.getLastPathSegment());
                break;
            case CATEGORY_1:
                vQueryBuilder.setTables(CategoryHelper.TABLE_NAME);
                break;
            case CATEGORY_2:
                vQueryBuilder.setTables(CategoryHelper.TABLE_NAME);
                vQueryBuilder.appendWhere(CategoryHelper._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        Cursor vCursor = vQueryBuilder.query(vDB, projection, selection, selectionArgs, null, null, sortOrder);
        vCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return vCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase vDB = dbHelper.getWritableDatabase();
        int vUriType = sUriMatcher.match(uri);
        int vU = 0;
        switch (vUriType) {
            case NOTE_2:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(NoteHelper.TABLE_NAME, values, NoteHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(NoteHelper.TABLE_NAME, values, NoteHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            case CATEGORY_2:
                if (TextUtils.isEmpty(selection))
                    vU = vDB.update(CategoryHelper.TABLE_NAME, values, CategoryHelper._ID + " = " + uri.getLastPathSegment(), null);
                else
                    vU = vDB.update(CategoryHelper.TABLE_NAME, values, CategoryHelper._ID + " = " + uri.getLastPathSegment() + " AND " + selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return vU;
    }
}
