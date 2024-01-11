package com.example.hireconnect;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.RangeSlider;

import java.util.Calendar;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView electrician, plumber, beautician, laundry;
    RangeSlider rangeSlider;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button timeButton;
    int hour, minute;


//    Button historyBtn, updateProfileBtn, pendingRequestsBtn, contactBtn, logoutBtn;

    Intent intent;
    String usernameTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.timeButton);

        //image views
        electrician = findViewById(R.id.electrician);
        plumber = findViewById(R.id.plumber);
        beautician = findViewById(R.id.beautician);
        laundry = findViewById(R.id.laundry);
        rangeSlider = findViewById(R.id.rangeSlider);

        //drawers, navigation, toolbar
        drawerLayout = findViewById(R.id.drawerLayout);
//        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open, R.string.navigation_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        //get intent info
        intent = getIntent();
        usernameTXT = intent.getStringExtra("usernameOfUser");

        //onclick for services
        electrician.setOnClickListener(v -> {
            searchFilter(0);
        });

        //onclick for services
        plumber.setOnClickListener(v -> {
            searchFilter(1);
        });

        //onclick for services
        beautician.setOnClickListener(v -> {
            searchFilter(2);
        });

        //onclick for services
        laundry.setOnClickListener(v -> {
            searchFilter(3);
        });


    }

    private void searchFilter(int select) {

        if (rangeSlider != null) {
            float costRangeUB = rangeSlider.getValues().get(1);
            float[] send = {select, costRangeUB};
            Intent userProfileIntent = new Intent(this, SearchResultsOfService.class);
            userProfileIntent.putExtra("service_ub_lb", send);
            userProfileIntent.putExtra("usernameOfUser", usernameTXT);
            startActivity(userProfileIntent);
        }

    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


}