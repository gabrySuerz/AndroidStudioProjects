package com.esame.suerzgabriele.Data;

import android.provider.BaseColumns;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class ReservationHelper implements BaseColumns {

    public static final String TABLE_NAME = "prenotazioni";
    public static final String NAME = "nomePrenotazione";
    public static final String PHONE = "nTelefono";
    public static final String PERSON = "nPersone";


    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER NOT NULL PRIMARY KEY, " +
            NAME + " TEXT NOT NULL, " +
            PHONE + " TEXT NOT NULL, " +
            PERSON+" INTEGER NOT NULL" + ");";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
