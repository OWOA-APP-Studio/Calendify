package com.owoa.calendify.schedule.read;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;

import android.widget.ListView;
import android.widget.TextView;

import com.owoa.calendify.friend.read.FriendReadActivity;
import com.owoa.calendify.intro.IntroActivity;
import com.owoa.calendify.schedule.create.ScheduleCreateActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleReadActivity extends AppCompatActivity {

    SimpleDateFormat mFormat_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat mFormat_month = new SimpleDateFormat("MM");
    SimpleDateFormat mFormat_day = new SimpleDateFormat("dd");

    SimpleDateFormat mFormat_hour = new SimpleDateFormat("hh");
    SimpleDateFormat mFormat_minute = new SimpleDateFormat("mm");

    SimpleDateFormat mFormat_week = new SimpleDateFormat("EE", Locale.getDefault());

    Date date = new Date();
    Date currentTime = Calendar.getInstance().getTime();

    private int year = Integer.parseInt(mFormat_year.format(date));
    private int month = Integer.parseInt(mFormat_month.format(date));
    private int day = Integer.parseInt(mFormat_day.format(date));
    private String week = mFormat_week.format(currentTime);

    private int hour = Integer.parseInt(mFormat_hour.format(date));
    private int minute = Integer.parseInt(mFormat_minute.format(date));

    Activity activity;
    CategoryReadPresenter categoryReadPresenter;
    String[] categories;
    ListView scheduleListView;
    String uid;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView date1;

    public String getUid() {
        return uid;
    }

    public void updateCategories() {
        categoryReadPresenter.loadUserCategory();
    }

    public CategoryReadPresenter getCategoryReadPresenter() {
        return categoryReadPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawerView);
        date1 = (TextView)findViewById(R.id.date_1);
        drawerLayout.setDrawerListener(listener);

        Intent intent = getIntent();
        uid = intent.getStringExtra(getString(R.string.uid));
        activity = this;

        scheduleListView = findViewById(R.id.schedule_list);

        date1.setText(String.format("%d년%d월%d일%s요일\n%d시%d분",year,month,day,week,hour,minute));

        RecyclerView categoryView = findViewById(R.id.category_list);

        categoryReadPresenter = new CategoryReadPresenter(uid, activity);
        categoryReadPresenter.setMainActivity(this);
        categoryReadPresenter.setRecyclerView(categoryView);
        categoryReadPresenter.loadUserCategory();

        ImageView scheduleCreateButton = findViewById(R.id.create_schedule_button);
        scheduleCreateButton.setOnClickListener(view -> {
            ArrayList categoryList = new ArrayList<>();
            categoryList.addAll(Arrays.asList(categories).subList(0, categories.length - 1));

            Intent intent1 = new Intent(ScheduleReadActivity.this, ScheduleCreateActivity.class);
            intent1.putExtra("categories", categoryList);
            intent1.putExtra(getString(R.string.uid), uid);
            startActivity(intent1);
        });

        TextView friendListButton = findViewById(R.id.nav_ex);
        friendListButton.setOnClickListener( view -> {
            ArrayList categoryList = new ArrayList<>();
            Log.d("SRA-CTG-CT", categories.length+"#-");
            categoryList.addAll(Arrays.asList(categories).subList(0, categories.length - 1));
            Intent intent2 = new Intent(ScheduleReadActivity.this, FriendReadActivity.class);
            intent2.putExtra("categories", categoryList);
            intent2.putExtra(getString(R.string.uid), uid);
            startActivity(intent2);
        });


        TextView logoutButton = findViewById(R.id.nav_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences autoSignInData = activity.getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = autoSignInData.edit();
                editor.clear();
                editor.commit();

                Intent introActivity = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(introActivity);

                finish();
            }
        });


        ImageView  scheduleNavigationButton = findViewById(R.id.create_navigation_button);
        scheduleNavigationButton.setOnClickListener(view -> drawerLayout.openDrawer(drawerView));
    }

    @NonNull
    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void loadSchedule(int index) {
        ScheduleReadPresenter presenter = new ScheduleReadPresenter(activity, categories, index);
        presenter.setListView(findViewById(R.id.schedule_list));
        presenter.setUid(uid);
        presenter.requestSchedules();
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }

    };


}