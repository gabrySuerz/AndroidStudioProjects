package com.esame.note_15_06_2017.Database;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 22/06/17.
 */

public class NoteHelper implements BaseColumns {

    public static final String TABLE_NAME = "note_table";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUDIOIMAGE = "audioImage";
    public static final String IMAGEPATH = "imagePath";
    public static final String SKETCH = "sketchPath";
    public static final String TYPE = "noteType";
    public static final String DATE = "dateCreated";
    public static final String REMINDER = "nextReminder";
    public static final String CATEGORY = "category";
    public static final String AUDIOEXIST = "cloudAudioExist";
    public static final String IMAGEEXIST = "cloudImageExist";
    public static final String SKETCHEXIST = "cloudSketchExist";

    public static String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            TITLE + " TEXT NOT NULL, " +
            CONTENT + " TEXT NOT NULL, " +
            AUDIOIMAGE + " TEXT NOT NULL, " +
            IMAGEPATH + " TEXT NOT NULL, " +
            SKETCH + " TEXT NOT NULL, " +
            TYPE + " TEXT NOT NULL, " +
            CATEGORY + " INTEGER NOT NULL, " +
            DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            REMINDER + " INTEGER NOT NULL" +
            AUDIOEXIST + " BOOLEAN NOT NULL, " +
            IMAGEEXIST + " BOOLEAN NOT NULL, " +
            SKETCHEXIST + " BOOLEAN NOT NULL, " +
            "FOREIGN KEY(" + CATEGORY + ") REFERENCES " + CategoryHelper.TABLE_NAME + "(" + CategoryHelper._ID + ")" +
            ");";

    public static String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
