package com.gabrysuerz.firebasenotes_05_07_2017.Models;

/**
 * Created by gabrysuerz on 20/06/17.
 */

public class User {

    private String emailAddress;
    private String userName;
    private String photoUrl;

    public User(String emailAddress, String userName, String photoUrl) {
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.photoUrl = photoUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
