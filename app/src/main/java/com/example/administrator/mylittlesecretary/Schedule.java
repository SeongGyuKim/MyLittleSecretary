package com.example.administrator.mylittlesecretary;

/**
 * Created by Administrator on 2016-09-24.
 */
public class Schedule {

    private int id;
    private String date;
    private String title;
    private int week;
    private int month;
    private String sTime;
    private String eTime;
    private String place;
    private int alarm;
    private int sound;

    public Schedule() {}

    public Schedule(int id, String date, String title, int week, int month, String sTime, String eTime, String  place, int alarm
    , int sound) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.week = week;
        this.month = month;
        this.sTime = sTime;
        this.eTime = eTime;
        this.place = place;
        this.alarm = alarm;
        this.sound = sound;
    }

    public Schedule(String date, String title, int week, int month, String sTime, String eTime, String  place, int alarm
            , int sound) {
        this.date = date;
        this.title = title;
        this.week = week;
        this.month = month;
        this.sTime = sTime;
        this.eTime = eTime;
        this.place = place;
        this.alarm = alarm;
        this.sound = sound;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getSTime() { return this.sTime; }

    public void setSTime(String sTime) {
        this.sTime = sTime;
    }

    public String getETime() {
        return this.eTime;
    }

    public void setETime(String eTime) {
        this.eTime = eTime;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getAlarm() {
        return this.alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public int getSound() {
        return this.sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }
}
