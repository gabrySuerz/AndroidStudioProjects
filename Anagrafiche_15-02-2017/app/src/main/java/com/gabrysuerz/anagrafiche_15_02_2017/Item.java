package com.gabrysuerz.anagrafiche_15_02_2017;


import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by gabrysuerz on 19/01/17.
 */

@Table(name = "PersonTable", id = BaseColumns._ID)
public class Item extends Model {

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

    @Column(name = NAME)
    public String mName;

    @Column(name = SURNAME)
    public String surname;

    @Column(name = BORN_DATE)
    public String date;

    @Column(name = EMAIL)
    public String email;

    @Column(name = PHONE)
    public String phone;

    @Column(name = ADDRESS)
    public String address;

    @Column(name = HOUSE_NUMBER)
    public String hNumber;

    @Column(name = CITY)
    public String city;

    @Column(name = CAP)
    public String cap;

    @Column(name = PROVINCE)
    public String province;

    @Column(name = LAT)
    public String lat;

    @Column(name = LONG)
    public String lon;


    public Item(){
        super();
    }

    public Item(String aName, String aSurname, String aDate, String aEmail, String aPhone, String aAddress, String aHNumber, String aCity, String aCap, String aProvince, String aLat, String aLon) {
        this.mName = aName;
        this.surname = aSurname;
        this.date = aDate;
        this.email = aEmail;
        this.phone = aPhone;
        this.address = aAddress;
        this.hNumber = aHNumber;
        this.city = aCity;
        this.cap = aCap;
        this.province = aProvince;
        this.lat = aLat;
        this.lon = aLon;
    }
}