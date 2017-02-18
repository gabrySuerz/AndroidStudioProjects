package com.example.gabry.listees11112016_2;

import java.util.Random;

/**
 * Created by gabry on 11/11/2016.
 */

public class NewItem {
    private int mID;
    private int mProgress;
    private int mStars;

    public NewItem(int aID) {
        mID = aID;
        Random vR = new Random();
        mProgress = vR.nextInt(100);
        mStars = vR.nextInt(5);
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getmStars() {
        return mStars;
    }

    public void setmStars(int mStars) {
        this.mStars = mStars;
    }
}
