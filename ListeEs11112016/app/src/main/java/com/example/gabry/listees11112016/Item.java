package com.example.gabry.listees11112016;

import java.util.Random;

/**
 * Created by gabry on 11/11/2016.
 */

public class Item {

    public int mId;
    public String mName;
    public int mLiter;

    public Item(int aId){
        mId = aId;
        mName = "Birra " + aId;
        Random vRand = new Random();
        mLiter = vRand.nextInt(10);
    }
}
