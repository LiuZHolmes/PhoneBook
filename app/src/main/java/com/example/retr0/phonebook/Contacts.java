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
    private int Num;
    Contacts(String Name, String HomeNumber, String PhoneNumber, String Address, String Birthday, int Num)
    {
        this.Name = Name;
        this.HomeNumber = HomeNumber;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
        this.Birthday = Birthday;
        this.Num = Num;
    }
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

    public int getNum() { return Num; }

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

    public void setNum(int i) { Num = i; }
}
