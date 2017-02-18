package com.gabrysuerz.realm_test_16_02_2017;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gabrysuerz on 16/02/17.
 */

public class Person extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;
    private String surname;
    private String date;
    private String email;
    private String phone;
    private String address;
    private String hNumber;
    private String city;
    private String cap;
    private String province;
    private String lat;
    private String lon;


    public Person() {
        super();
    }

    public Person(String aName, String aSurname, String aDate, String aEmail, String aPhone, String aAddress, String aHNumber, String aCity, String aCap, String aProvince, String aLat, String aLon) {
        this.name = aName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String gethNumber() {
        return hNumber;
    }

    public void sethNumber(String hNumber) {
        this.hNumber = hNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
