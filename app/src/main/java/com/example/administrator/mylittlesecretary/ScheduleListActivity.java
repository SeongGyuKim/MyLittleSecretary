package com.example.administrator.mylittlesecretary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016-10-06.
 */

public class ScheduleListActivity extends Activity {

    private ListView sListView = null;
    private ListViewAdapter sAdapter = null;

    private String[] navItems = {"캘린더", "일정 입력", "일정 확인"};
    private ListView lvNavList;
    private FrameLayout flContainer;
    private boolean isopen = false;
    private DrawerLayout dlDrawer;

    private TextView talkbox;
    private String talkArray[];
    private Random random;
    private int randomNumber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulelist);

        final ScheduleDataHelper db = new ScheduleDataHelper(this);

        sListView = (ListView) findViewById(R.id.sList);

        sAdapter = new ListViewAdapter(this);
        sListView.setAdapter(sAdapter);

        final List<Schedule> schedules = db.getAllschedules();

        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id){
                final ListData sData = sAdapter.sListData.get(position);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

                alertDialogBuilder.setTitle("일정 삭제");

                alertDialogBuilder
                        .setMessage("정말 삭제할껀가요...?")
                        .setCancelable(false)
                        .setPositiveButton("그래",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        sAdapter.remove(position);
                                        int db_id = sData.id;
                                        Log.d("지워진 db id", Integer.toString(db_id));
                                        deleteSchedule(db_id, db);
                                    }
                                })
                        .setNegativeButton("아니야",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        for (Schedule cn : schedules) {
            sAdapter.addItem(cn.getID(), cn.getTitle(), cn.getSTime(), cn.getETime(), cn.getPlace(), cn.getAlarm());
            String log = "Id: " + cn.getID() + " ,Date: " + cn.getDate() + " ,Title: " + cn.getTitle()
                    + " ,STime: " + cn.getSTime() + " ,ETime: " + cn.getETime() + " ,Place: " + cn.getPlace()
                    + ", Alram: " + cn.getAlarm() + ", Sound : " + cn.getSound();
            Log.d("Schedules: ", log);
        }

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (FrameLayout) findViewById(R.id.fl_activity_main_container);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));

        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);

        View button1 = findViewById(R.id.menubutton);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dlDrawer.isDrawerOpen(Gravity.LEFT)) {
                    dlDrawer.closeDrawer(Gravity.LEFT);
                } else {
                    dlDrawer.openDrawer(Gravity.LEFT);
                }
                isopen = !isopen;
            }
        });

        talkArray = new String[9];
        talkArray = getResources().getStringArray(R.array.talkArray_list);

        random = new Random();
        randomNumber = random.nextInt(9);

        talkbox = (TextView) findViewById(R.id.talkbox);
        talkbox.setText(talkArray[randomNumber]);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ScheduleList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.mylittlesecretary/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ScheduleList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.mylittlesecretary/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class ViewHolder {
        public TextView sTitle;
        public TextView sSTime;
        public TextView sETime;
        public TextView sPlace;
        public TextView sAlarm;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context sContext = null;
        private ArrayList<ListData> sListData = new ArrayList<ListData>();

        public ListViewAdapter(Context sContext) {
            super();
            this.sContext = sContext;
        }

        @Override
        public int getCount() {
            return sListData.size();
        }

        @Override
        public Object getItem(int position) {
            return sListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.schedulelistview_item, null);

                holder.sTitle = (TextView) convertView.findViewById(R.id.item_title);
                holder.sSTime = (TextView) convertView.findViewById(R.id.item_sTime);
                holder.sETime = (TextView) convertView.findViewById(R.id.item_eTime);
                holder.sPlace = (TextView) convertView.findViewById(R.id.item_place);
                holder.sAlarm = (TextView) convertView.findViewById(R.id.item_alarm);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData sData = sListData.get(position);

            if (sData.sTitle != null) {
                holder.sTitle.setVisibility(View.VISIBLE);
                holder.sTitle.setText(sData.sTitle);
            } else {
                holder.sTitle.setVisibility(View.GONE);
            }

            holder.sSTime.setText(sData.sSTime);
            holder.sETime.setText(sData.sETime);
            holder.sPlace.setText(sData.sPlace);
            holder.sAlarm.setText(sData.sAlarm + "분 전 알림");

            return convertView;
        }

        public void addItem(int id, String sTitle, String sSTime, String sETime, String sPlace, int sAlarm) {
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.id = id;
            addInfo.sTitle = sTitle;
            addInfo.sSTime = sSTime;
            addInfo.sETime = sETime;
            addInfo.sPlace = sPlace;
            addInfo.sAlarm = Integer.toString(sAlarm);

            sListData.add(addInfo);

            Log.d("addItem : ", "성공");
        }

        public void remove(int position) {
            sListData.remove(position);
            dataChange();
        }

        public void dataChange() {
            sAdapter.notifyDataSetChanged();
        }
    }
    protected void onPostCreate(Bundle savedInstanceState) {
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
                    Intent intent = new Intent(ScheduleListActivity.this, CalendarActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(ScheduleListActivity.this, EnterActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case 2:
                    dlDrawer.closeDrawer(Gravity.LEFT);
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }

    public void onClickSimvol(View view) {
        randomNumber = random.nextInt(9);

        talkbox.setText(talkArray[randomNumber]);
    }

    public void deleteSchedule(int id, ScheduleDataHelper db) {
        db.deleteContact(db.getSchedule(id));
        Log.d("deleteSchedule", "성공");
    }
}


//http://recipes4dev.tistory.com/48