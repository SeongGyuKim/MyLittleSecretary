package com.example.administrator.mylittlesecretary;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.mylittlesecretary.ScheduleDB.MyLittleSecretary;

/**
 * Created by Administrator on 2016-09-24.
 */
public class ScheduleDataHelper extends SQLiteOpenHelper {

    public ScheduleDataHelper(Context context) {
        super(context, MyLittleSecretary.DB_NAME, null, MyLittleSecretary.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SCHEDULE_TABLE = " CREATE TABLE " + MyLittleSecretary.TABLE_NAME
                + "(" + MyLittleSecretary.COLUMN_NAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyLittleSecretary.COLUMN_NAME_DATE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_TITLE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_WEEK + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_MONTH + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_STIME + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_ETIME + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_PLACE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_ALARM + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_SOUND + " INTEGER" + ")";
        Log.d("Query : ", CREATE_SCHEDULE_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_SCHEDULE_TABLE = " CREATE TABLE " + MyLittleSecretary.TABLE_NAME
                + "(" + MyLittleSecretary.COLUMN_NAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyLittleSecretary.COLUMN_NAME_DATE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_TITLE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_WEEK + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_MONTH + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_STIME + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_ETIME + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_PLACE + " TEXT,"
                + MyLittleSecretary.COLUMN_NAME_ALARM + " INTEGER,"
                + MyLittleSecretary.COLUMN_NAME_SOUND + " INTEGER" + ")";
        Log.d("Query : ", CREATE_SCHEDULE_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    public int addSchedule(Schedule schedule) {
        int result;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyLittleSecretary.COLUMN_NAME_DATE, schedule.getDate());
        values.put(MyLittleSecretary.COLUMN_NAME_TITLE, schedule.getTitle());
        values.put(MyLittleSecretary.COLUMN_NAME_WEEK, schedule.getWeek());
        values.put(MyLittleSecretary.COLUMN_NAME_MONTH, schedule.getMonth());
        values.put(MyLittleSecretary.COLUMN_NAME_STIME, schedule.getSTime());
        values.put(MyLittleSecretary.COLUMN_NAME_ETIME, schedule.getETime());
        values.put(MyLittleSecretary.COLUMN_NAME_PLACE, schedule.getPlace());
        values.put(MyLittleSecretary.COLUMN_NAME_ALARM, schedule.getAlarm());
        values.put(MyLittleSecretary.COLUMN_NAME_SOUND, schedule.getSound());

        result = (int) db.insert(MyLittleSecretary.TABLE_NAME, null, values);

        Log.d("Qurey", "add Schedule");
        db.close();

        return result;
    }

    public Schedule getSchedule(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MyLittleSecretary.TABLE_NAME, new String[] {
                MyLittleSecretary.COLUMN_NAME_ID, MyLittleSecretary.COLUMN_NAME_DATE,
                MyLittleSecretary.COLUMN_NAME_TITLE, MyLittleSecretary.COLUMN_NAME_WEEK,
                MyLittleSecretary.COLUMN_NAME_MONTH, MyLittleSecretary.COLUMN_NAME_STIME,
                MyLittleSecretary.COLUMN_NAME_ETIME, MyLittleSecretary.COLUMN_NAME_PLACE,
                MyLittleSecretary.COLUMN_NAME_ALARM, MyLittleSecretary.COLUMN_NAME_SOUND}
                , MyLittleSecretary.COLUMN_NAME_ID + "=?", new String[] { String.valueOf(id)},
                null, null, null, null);

        Log.d("Query", "getSchedule by id");

        Schedule schedule = null;

        if (cursor != null && cursor.moveToFirst()) {
            schedule = new Schedule(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Integer.parseInt(cursor.getString(8)),
                    Integer.parseInt(cursor.getString(9)));
        } else {
            Log.d("Cursor", "getSchedule by id : cursor is null");
        }

        return schedule;
    }

    public Schedule getSchedule(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MyLittleSecretary.TABLE_NAME, new String[] {
                        MyLittleSecretary.COLUMN_NAME_ID, MyLittleSecretary.COLUMN_NAME_DATE,
                        MyLittleSecretary.COLUMN_NAME_TITLE, MyLittleSecretary.COLUMN_NAME_WEEK,
                        MyLittleSecretary.COLUMN_NAME_MONTH, MyLittleSecretary.COLUMN_NAME_STIME,
                        MyLittleSecretary.COLUMN_NAME_ETIME, MyLittleSecretary.COLUMN_NAME_PLACE,
                        MyLittleSecretary.COLUMN_NAME_ALARM, MyLittleSecretary.COLUMN_NAME_SOUND}
                , MyLittleSecretary.COLUMN_NAME_DATE + "=?", new String[] { String.valueOf(date) }, null, null,
                null, null);

        Log.d("Query", "getSchedule by date");

        Schedule schedule = null;

        if (cursor != null && cursor.moveToFirst()) {

            schedule = new Schedule(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Integer.parseInt(cursor.getString(8)),
                    Integer.parseInt(cursor.getString(9)));
        } else {
            Log.d("Cursor","getShedule by date : cursor is null");
        }

        return schedule;

    }

    public void dropTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DROP TABLE IF EXISTS " + MyLittleSecretary.TABLE_NAME;
        db.execSQL(query);
    }

    // get all Days
    public List<Schedule> getAllschedules() {
        List<Schedule> schedulesList = new ArrayList<Schedule>();

        String selectQuery = "SELECT  * FROM " + MyLittleSecretary.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Query : ", selectQuery);

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setID(Integer.parseInt(cursor.getString(0)));
                schedule.setDate(cursor.getString(1));
                schedule.setTitle(cursor.getString(2));
                schedule.setWeek(Integer.parseInt(cursor.getString(3)));
                schedule.setMonth(Integer.parseInt(cursor.getString(4)));
                schedule.setSTime(cursor.getString(5));
                schedule.setETime(cursor.getString(6));
                schedule.setPlace(cursor.getString(7));
                schedule.setAlarm(Integer.parseInt(cursor.getString(8)));
                schedule.setSound(Integer.parseInt(cursor.getString(9)));

                // Adding contact to list
                schedulesList.add(schedule);
            } while (cursor.moveToNext());
        }
        return schedulesList;
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + MyLittleSecretary.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("Query : ", countQuery);

        cursor.close();

        return cursor.getCount();
    }

    public int updateContact(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyLittleSecretary.COLUMN_NAME_DATE, schedule.getDate());
        values.put(MyLittleSecretary.COLUMN_NAME_TITLE, schedule.getTitle());
        values.put(MyLittleSecretary.COLUMN_NAME_WEEK, schedule.getWeek());
        values.put(MyLittleSecretary.COLUMN_NAME_MONTH, schedule.getMonth());
        values.put(MyLittleSecretary.COLUMN_NAME_STIME, schedule.getSTime());
        values.put(MyLittleSecretary.COLUMN_NAME_ETIME, schedule.getETime());
        values.put(MyLittleSecretary.COLUMN_NAME_PLACE, schedule.getPlace());
        values.put(MyLittleSecretary.COLUMN_NAME_ALARM, schedule.getAlarm());
        values.put(MyLittleSecretary.COLUMN_NAME_SOUND, schedule.getSound());

        // updating row
        return db.update(MyLittleSecretary.TABLE_NAME, values,
                MyLittleSecretary.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(schedule.getID()) });
    }

    public void deleteContact(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MyLittleSecretary.TABLE_NAME, MyLittleSecretary.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(schedule.getID()) });
        db.close();
    }

    public void onCreate(Bundle sacedIstanceState) {
        // TODO Auto-generated method stub

    }

}
