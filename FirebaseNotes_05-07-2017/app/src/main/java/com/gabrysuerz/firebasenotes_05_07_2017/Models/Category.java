package com.gabrysuerz.firebasenotes_05_07_2017.Models;

/**
 * Created by gabrysuerz on 20/06/17.
 */

public class Category {

    private String categoryId;
    private String categoryName;
    private int count;

    public Category(String UID, String name) {
        this.categoryId = UID;
        this.categoryName = name;
        this.count = 0;
    }

    public Category() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
