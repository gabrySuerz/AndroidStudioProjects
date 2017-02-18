package com.example.gabry.tantodevocancellarlo.Database;

import java.util.Random;

/**
 * Created by gabry on 13/12/2016.
 */

public class Item {
    public long id;
    public String first;
    public String second;

    public Item() {
    }

    public Item(String aFirst, String aSecond) {
        first = aFirst;
        second = aSecond;
    }

    public Item(long aID, String aFirst, String aSecond) {
        id = aID;
        first = aFirst;
        second = aSecond;
    }

    public static Item createItem() {
        Random vRand = new Random();
        String vFirst = "First: " + vRand.nextInt();
        String vSecond = "Second: " + vRand.nextInt();
        return new Item(vFirst, vSecond);
    }

}
