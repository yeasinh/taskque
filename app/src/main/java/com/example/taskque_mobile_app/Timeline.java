package com.example.taskque_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Timeline extends AppCompatActivity  {

    DatePickerDialog datePickerDialog;
    Button datePickerBtn,timePickerBtn,btnSet,btnCancel;
    TextView tvDate;
    TimePicker timePicker;
    int hour,minute,TaskID;

    int Month,Year,Day,type=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        Bundle bundle = getIntent().getExtras();

        TaskID = bundle.getInt("TaskID");
        //dropdown menu starts

        timePicker = findViewById(R.id.timePicker1);
        btnSet = findViewById(R.id.setbtn_Timeline);
        tvDate = findViewById(R.id.datePickerText_id);
        btnCancel = findViewById(R.id.cancelBtn_timeline);
        Spinner spinner;
        ArrayAdapter<CharSequence> adapter;

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " Selected", Toast.LENGTH_LONG).show();

                /*replaceFragment(new OneTime()){


                }*/
                if(parent.getItemAtPosition(position).toString().equals("One Time"))
                {
                    datePickerBtn.setVisibility(View.VISIBLE);
                    tvDate.setVisibility(View.VISIBLE);
                    type=1;

                }
                else if(parent.getItemAtPosition(position).toString().equals("Daily"))
                {
                    datePickerBtn.setVisibility(View.GONE);
                    tvDate.setVisibility(View.GONE);
                    type=2;


                }
                else if(parent.getItemAtPosition(position).toString().equals("Weekly"))
                {
                    datePickerBtn.setVisibility(View.GONE);
                    tvDate.setVisibility(View.GONE);
                    type=3;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int h = timePicker.getCurrentHour();
                int m = timePicker.getCurrentMinute();
                TasksDB db = new TasksDB(Timeline.this);
                db.open();

                if(type==1)
                {
                    db.entryTimers(TaskID,Year,Month,Day,h,m,"One Time");

                    int timerID = db.getLatestTimerID();

                    int requestCode  = db.getRequestCode();
                    db.updateRequestCode(requestCode+1);

                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.YEAR,Year);
                    time.set(Calendar.MONTH,Month);
                    time.set(Calendar.DAY_OF_MONTH,Day);
                    time.set(Calendar.HOUR_OF_DAY,h);
                    time.set(Calendar.MINUTE,m);
                    time.set(Calendar.SECOND,0);
                    long alarmTime = time.getTimeInMillis();


                    Intent intent = new Intent(Timeline.this, AlarmReceiver.class);
                    intent.putExtra("taskId", TaskID);
                    intent.putExtra("timerId", timerID);

                    // getBroadcast(context, requestCode, intent, flags)
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(Timeline.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime,alarmIntent);
                }
                else if(type==2)
                {
                    db.entryTimers(TaskID,Year,Month,Day,h,m,"Daily");

                    int timerID = db.getLatestTimerID();

                    int requestCode  = db.getRequestCode();
                    db.updateRequestCode(requestCode+1);

                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.HOUR_OF_DAY,h);
                    time.set(Calendar.MINUTE,m);
                    time.set(Calendar.SECOND,0);
                    long alarmTime = time.getTimeInMillis();


                    Intent intent = new Intent(Timeline.this, AlarmReceiver.class);
                    intent.putExtra("taskId", TaskID);
                    intent.putExtra("timerId", timerID);

                    // getBroadcast(context, requestCode, intent, flags)
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(Timeline.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,alarmTime,AlarmManager.INTERVAL_DAY,alarmIntent);
                }
                else if(type==3)
                {
                    db.entryTimers(TaskID,Year,Month,Day,h,m,"Weekly");

                    int timerID = db.getLatestTimerID();

                    int requestCode  = db.getRequestCode();
                    db.updateRequestCode(requestCode+1);

                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.HOUR_OF_DAY,h);
                    time.set(Calendar.MINUTE,m);
                    time.set(Calendar.SECOND,0);
                    long alarmTime = time.getTimeInMillis();


                    Intent intent = new Intent(Timeline.this, AlarmReceiver.class);
                    intent.putExtra("taskId", TaskID);
                    intent.putExtra("timerId", timerID);

                    // getBroadcast(context, requestCode, intent, flags)
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(Timeline.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,alarmTime,7*AlarmManager.INTERVAL_DAY,alarmIntent);
                }
                db.close();


                finish();
            }

        });

        //drop down menu ends

        //datePicker and timepicker starts

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initDatePicker();
        datePickerBtn=findViewById(R.id.datePickerButton_id);
        datePickerBtn.setText(getTodaysDate());




    }

    private String getTodaysDate() {

        Calendar cal=Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month=month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {


        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Year = year;
                Month = month;
                Day = dayOfMonth;
                Toast.makeText(Timeline.this, year+" "+month+" "+dayOfMonth, Toast.LENGTH_SHORT).show();
                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datePickerBtn.setText(date);

            }
        };

        Calendar cal=Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Year = year;
        Month = month;
        Day = day;

        int style= AlertDialog.THEME_HOLO_DARK;
        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month)+" "+dayOfMonth+" "+year;
    }

    private String getMonthFormat(int month) {

        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";

        return "JUL";


    }

    public void openDatePicker(View view) {

        datePickerDialog.show();
    }

    public void popTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour=selectedHour;
                minute=selectedMinute;
                timePickerBtn.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));

            }
        };

        int style2=AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog=new TimePickerDialog(this,style2,onTimeSetListener,hour,minute,false);
        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }




    //date picker ends




}