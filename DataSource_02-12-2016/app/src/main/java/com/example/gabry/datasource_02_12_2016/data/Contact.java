package com.example.gabry.datasource_02_12_2016.data;

import java.util.Random;

/**
 * Created by gabry on 02/12/2016.
 */

public class Contact {
    public long mID;
    public String mName;
    public String mSurname;

    public Contact(){}

    public Contact(String aName, String aSurname) {
        mName = aName;
        mSurname = aSurname;
    }

    public Contact(long aID, String aName, String aSurname) {
        mID = aID;
        mName = aName;
        mSurname = aSurname;
    }

    public static Contact createContacts(){
        Random vRand=new Random();
        String vName="Nome: "+vRand.nextInt();
        String vSurname="Cognome: "+vRand.nextInt();
        return new Contact(vName,vSurname);
    }
}
