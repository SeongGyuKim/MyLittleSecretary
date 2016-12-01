package com.example.administrator.mylittlesecretary;

/**
 * Created by Administrator on 2016-08-11.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class EnterActivity extends Activity {

    EditText title;
    TextView date;
    TextView sTime;
    TextView eTime;
    EditText place;
    int alram;
    CheckBox cb1;
    CheckBox cb2;
    int sound = 0;

    private TextView mDateDisplay;
    private Button mPickDate;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    private Button mSPickTime;
    private Button mEPickTime;

    private int mHour;
    private int mMinute;
    static final int STIME_DIALOG_ID = 1;
    static final int ETIME_DIALOG_ID = 2;

    private int alram_mHour;
    private int alram_mMinute;

    private String[] navItems = {"캘린더", "일정 입력", "일정 확인"};
    private ListView lvNavList;
    private FrameLayout flContainer;
    private boolean isopen = false;
    private DrawerLayout dlDrawer;

    AlarmManager mAlarmMgr;

    private String talkArray[];
    private Random random;
    private int randomNumber;

    private TextView talkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);


        title = (EditText) findViewById(R.id.enterschedule_name);
        date = (TextView) findViewById(R.id.enterschedule_date);
        sTime = (TextView) findViewById(R.id.enterschedule_sTime);
        eTime = (TextView) findViewById(R.id.enterschedule_eTime);
        place = (EditText) findViewById(R.id.enterschedule_place);
        cb1 = (CheckBox) findViewById(R.id.enterschedule_sound);
        cb2 = (CheckBox) findViewById(R.id.enterschedule_vibe);

        String[] str = getResources().getStringArray(R.array.alarm);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, str);
        Spinner spi = (Spinner) findViewById(R.id.enterschedule_Alarm);
        spi.setAdapter(adapter);
        spi.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected
                            (AdapterView<?> parrent, View view, int position, long id) {
                        print(view, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        lvNavList = (ListView)findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (FrameLayout)findViewById(R.id.fl_activity_main_container);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout)findViewById(R.id.dl_activity_main_drawer);

        View button1 = findViewById(R.id.menubutton);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(dlDrawer.isDrawerOpen(Gravity.LEFT)){
                    dlDrawer.closeDrawer(Gravity.LEFT);
                }else{
                    dlDrawer.openDrawer(Gravity.LEFT);
                }
                isopen = !isopen;
            }
        });

        mDateDisplay = (TextView) findViewById(R.id.enterschedule_date);
        mPickDate = (Button) findViewById(R.id.pickDate);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String checkIntet = getIntent().getStringExtra("intent_date");
        if (checkIntet != null) {
            Log.d("checkIntent", "not null");
            mDateDisplay.setText(checkIntet);
            Log.d("setText", "성공");
        } else {
            Log.d("checkIntent", "null");
            updateDateDisplay();
        }

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        mSPickTime = (Button) findViewById(R.id.pickTime);

        mEPickTime = (Button) findViewById(R.id.picktime2);

        // add a click listener to the button
        mSPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(STIME_DIALOG_ID);
            }
        });

        mEPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(ETIME_DIALOG_ID);
            }
        });

        updateTimeDisplay(sTime);
        initETimeDisplay(eTime);

        talkArray = new String[9];
        talkArray = getResources().getStringArray(R.array.talkArray_Enter);

        random = new Random();
        randomNumber = random.nextInt(9);

        talkbox = (TextView) findViewById(R.id.talkbox);
        talkbox.setText(talkArray[randomNumber]);
    }

    public void print(View v, int position) {
        Spinner sp = (Spinner) findViewById(R.id.enterschedule_Alarm);
        String res = "";
        if (sp.getSelectedItemPosition() > 0) {
            res = (String) sp.getAdapter().getItem(sp.getSelectedItemPosition());
        }

        alram = alarmValue((String) sp.getSelectedItem());

    }

    public int alarmValue(String sv) {
        switch (sv) {
            case "없음":
                Log.d("alarmValue : ", "0");
                return 0;

            case "5분 전":
                Log.d("alarmValue : ", "5");
                return 5;

            case "10분 전":
                Log.d("alarmValue : ", "10");
                return 10;

            case "30분 전":
                Log.d("alarmValue : ", "30");
                return 30;

            case "1시간 전":
                Log.d("alarmValue : ", "60");
                return 60;

            default:
                return -1;
        }
    }

    public void soundValue() {

        if (cb1.isChecked() == true)
            sound += 1;

        if (cb2.isChecked() == true)
            sound += 2;

        Log.d("soundValue", Integer.toString(sound));
    }


    public void saveSchedules(View view) {
        soundValue();

        ScheduleDataHelper db = new ScheduleDataHelper(this);
        Schedule schedule = new Schedule();

        schedule.setTitle(title.getText().toString());
        schedule.setSTime(sTime.getText().toString());
        schedule.setETime(eTime.getText().toString());
        schedule.setPlace(place.getText().toString());
        schedule.setDate(date.getText().toString());
        schedule.setAlarm(alram);
        schedule.setSound(sound);

        db.addSchedule(schedule);


        List<Schedule> schedules = db.getAllschedules();

        for (Schedule cn : schedules) {
            String log = "Id: " + cn.getID() + " ,Date: " + cn.getDate() + " ,Title: " + cn.getTitle()
                    + " ,STime: " + cn.getSTime() + " ,ETime: " + cn.getETime() + " ,Place: " + cn.getPlace()
                    + ", Alram: " + cn.getAlarm() + ", Sound : " + cn.getSound();

            Log.d("Schedules: ", log);
        }
        if(alram != 0 && alram != -1) {
            setAlarm();
            Log.d("setAlarm", "호출 됨");
        }
        Schedule tempSchedule;

        tempSchedule = db.getSchedule(schedule.getDate());

        Intent intent = new Intent(this, ScheduleListActivity.class);

        startActivity(intent);
        finish();
    }

    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(EnterActivity.this, CalendarActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    dlDrawer.closeDrawer(Gravity.LEFT);
                    break;
                case 2:
                    Intent intent2 = new Intent(EnterActivity.this, ScheduleListActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case STIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mSTimeSetListener, mHour, mMinute, false);
            case ETIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mETimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

    // updates the date we display in the TextView
    private void updateDateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append(".")
                        .append(mMonth + 1).append(".")
                        .append(mDay).append(" "));
        Log.d("년월일 업데이트 할 때", Integer.toString(mYear) + "." + Integer.toString(mMonth + 1) +
                "." + Integer.toString(mDay) + "//" + Integer.toString(mHour) + "시" +
                Integer.toString(mMinute) + "분");
    }

    // updates the time we display in the TextView
    private void updateTimeDisplay(TextView textView) {
        textView.setText(
                new StringBuilder()
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));
        if(textView == sTime) {
            alram_mHour = mHour;
            alram_mMinute = mMinute;
        }
    }

    private void initETimeDisplay(TextView textView) {
        textView.setText(
                new StringBuilder()
                    .append(pad(mHour + 1)).append(":")
                    .append(pad(mMinute)));
    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDateDisplay();

                }
            };

    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mSTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateTimeDisplay(sTime);
                }
            };

    private TimePickerDialog.OnTimeSetListener mETimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateTimeDisplay(eTime);
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private  void setAlarm() {
        int tempMinute;
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intentAlarm = new Intent(EnterActivity.this, BroadcastD.class);

        intentAlarm.putExtra("title", title.getText().toString());

        if(place.toString() != null)
            intentAlarm.putExtra("place", place.getText().toString());

        intentAlarm.putExtra("sound", sound);

        PendingIntent sender = PendingIntent.getBroadcast(EnterActivity.this, 0, intentAlarm, PendingIntent.FLAG_ONE_SHOT);

        Calendar calendar2 = Calendar.getInstance();
        if(alram == 60) {

            calendar2.set(mYear, mMonth, mDay, alram_mHour - 1, alram_mMinute);
            Log.d("설정한 시간", Integer.toString(mYear) + "." + Integer.toString(mMonth +1) +
                    "." + Integer.toString(mDay) + "//" + Integer.toString(mHour) + "시" +
                    Integer.toString(mMinute) + "분");
        }
        else if(alram != -1 && alram != 0) {
            tempMinute = alram_mMinute - alram;

            if (tempMinute < 0) {
                calendar2.set(mYear, mMonth, mDay, alram_mHour - 1, 60 + tempMinute);
                Log.d("설정한 시간", Integer.toString(mYear) + "." + Integer.toString(mMonth + 1) +
                        "." + Integer.toString(mDay) + "//" + Integer.toString(mHour) + "시" +
                        Integer.toString(mMinute) + "분");
            }
            else {
                calendar2.set(mYear, mMonth, mDay, alram_mHour, tempMinute);
                Log.d("설정한 시간", Integer.toString(mYear) + "." + Integer.toString(mMonth + 1) +
                        "." + Integer.toString(mDay) + "//" + Integer.toString(mHour) + "시" +
                        Integer.toString(mMinute) + "분");
            }
        }
        Log.d("알림 시간", calendar2.getTime().toString());

        am.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), sender);
    }

    public void onClickSimvol(View view) {
        randomNumber = random.nextInt(9);

        talkbox.setText(talkArray[randomNumber]);
    }
}
