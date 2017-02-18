package com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 19/01/17.
 */

public class PersonHelper implements BaseColumns {

    public final static String TABLE_NAME_ONE = "PersonTable";
    public final static String NAME = "Nome";
    public final static String SURNAME = "Cognome";
    public final static String BORN_DATE = "Data";
    public final static String EMAIL = "Email";
    public final static String PHONE = "Telefono";
    public final static String ADDRESS = "Indirizzo";
    public final static String HOUSE_NUMBER = "Civico";
    public final static String CITY = "Città";
    public final static String CAP = "CAP";
    public final static String PROVINCE = "Provincia";
    public final static String LAT = "Latitudine";
    public final static String LONG = "Longitudine";

    public final static String CREATE_PERSON_QUERY = "CREATE TABLE " +
            TABLE_NAME_ONE + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            SURNAME + " TEXT NOT NULL, " +
            BORN_DATE + " TEXT NOT NULL, " +
            EMAIL + " TEXT NOT NULL, " +
            PHONE + " TEXT NOT NULL, " +
            ADDRESS + " TEXT NOT NULL, " +
            HOUSE_NUMBER + " TEXT NOT NULL, " +
            CITY + " TEXT NOT NULL, " +
            CAP + " TEXT NOT NULL, " +
            PROVINCE + " TEXT NOT NULL, " +
            LAT + " TEXT NOT NULL, " +
            LONG + " TEXT NOT NULL" +
            ");";

}
