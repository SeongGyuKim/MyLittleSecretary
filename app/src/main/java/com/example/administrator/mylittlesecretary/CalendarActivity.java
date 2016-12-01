package com.example.administrator.mylittlesecretary;


import android.app.Activity;
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
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class CalendarActivity extends Activity {
    private String[] navItems = {"캘린더", "일정 입력", "일정 확인"};
    private ListView lvNavList;
    private FrameLayout flContainer;
    private boolean isopen = false;
    private DrawerLayout dlDrawer;

    private TextView talkbox;
    private String talkArray[];
    private Random random;
    private int randomNumber;

    private Button menubutton;
    private LinearLayout topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        menubutton = (Button)findViewById(R.id.menubutton);
        topbar = (LinearLayout)findViewById(R.id.topbar);
        topbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbar));
        menubutton.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubutton));

        CalendarView calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.enterbg));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView View, int year, int month, int dayOfMonth) {
                Integer i_year = new Integer(year);
                Integer i_month = new Integer(month+1);
                Integer i_dayOfMonth = new Integer(dayOfMonth);
                String s = i_year.toString() + "." + i_month.toString()
                        + "." + i_dayOfMonth.toString() + " ";

                Log.d("integer 변환", "성공");

                Intent intent = new Intent(CalendarActivity.this, EnterActivity.class);
                intent.putExtra("intent_date", i_year.toString() + "." + i_month.toString()
                    + "." + i_dayOfMonth.toString() + " ");
                Log.d("putExtra", s);
                startActivity(intent);
            }
        });

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

        talkArray = new String[9];
        talkArray = getResources().getStringArray(R.array.talkArray_calendar);

        random = new Random();
        randomNumber = random.nextInt(9);

        talkbox = (TextView) findViewById(R.id.talkbox);
        talkbox.setText(talkArray[randomNumber]);
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
                    dlDrawer.closeDrawer(Gravity.LEFT);
                    break;
                case 1:
                    Intent intent = new Intent(CalendarActivity.this, EnterActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    Intent intent2 = new Intent(CalendarActivity.this, ScheduleListActivity.class);
                    startActivity(intent2);
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }
    public void onClickSimvol(View view) {
        randomNumber = random.nextInt(9);

        talkbox.setText(talkArray[randomNumber]);
    }

}
