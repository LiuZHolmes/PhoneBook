package com.example.retr0.phonebook;

/**
 * Created by Shunchao on 2018/6/20.
 */

public class Contacts {
    private String Name;
    private String HomeNumber;
    private String PhoneNumber;
    private String Address;
    private String Birthday;

    public String getName() {
        return Name;
    }

    public String getHomeNumber() {
        return HomeNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setName(String s) {
        Name = s;
    }

    public void setHomeNumber(String s) {
        HomeNumber = s;
    }

    public void setPhoneNumber(String s) {
        PhoneNumber = s;
    }

    public void setAddress(String s) {
        Address = s;
    }

    public void setBirthday(String s) {
        Birthday = s;
    }
}
