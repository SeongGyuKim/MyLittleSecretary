package com.example.administrator.mylittlesecretary;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016-09-24.
 */
public final class ScheduleDB {

    public ScheduleDB() {}

    public static abstract class MyLittleSecretary implements BaseColumns {

        public static final String DB_NAME = "MyLittleSecretary";
        public static final int DB_VERSION = 1;

        public static final String TABLE_NAME = "ScheduleDB";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_WEEK = "week";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_STIME = "sTime";
        public static final String COLUMN_NAME_ETIME = "eTime";
        public static final String COLUMN_NAME_PLACE = "place";
        public static final String COLUMN_NAME_ALARM = "alarm";
        public static final String COLUMN_NAME_SOUND = "sound";
    }
}

