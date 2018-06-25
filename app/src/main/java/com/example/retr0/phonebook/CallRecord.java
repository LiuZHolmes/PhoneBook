package com.example.retr0.phonebook;

/**
 * Created by Mirai on 2018/6/20.
 */

public class CallRecord {
    /*通话记录聚合展示，需要包含姓名/号码、时间、类型、归属地等基本信息；*/
    private String number;
    private String date;
    private String time;
    private String type;
    private String location;
    CallRecord(String number, String date, String time, String type, String location)
    {
        this.number = number;
        this.date = date;
        this.time = time;
        this.type = type;
        this.location = location;
    }
    public String getNumber(){return this.number;}
    public String getDate(){return this.date;}
    public String getTime(){return this.time;}
    public String getType(){return this.type;}
    public String getLocation(){return this.location;}
}
