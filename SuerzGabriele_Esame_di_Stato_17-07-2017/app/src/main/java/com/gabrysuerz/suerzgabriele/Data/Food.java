package com.gabrysuerz.suerzgabriele.Data;

/**
 * Created by gabrysuerz on 17/07/17.
 */

public class Food {

    private int mID;
    private String mFoodName;
    private int mQuantity;
    private int mCost;

    public Food(int mID, String mFoodName, int mQuantity, int mCost) {
        this.mID = mID;
        this.mFoodName = mFoodName;
        this.mQuantity = mQuantity;
        this.mCost = mCost;
    }

    public int getmID() {
        return mID;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public int getmCost() {
        return mCost;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }
}
