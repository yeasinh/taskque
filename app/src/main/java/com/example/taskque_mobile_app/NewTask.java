package com.example.taskque_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class NewTask extends AppCompatActivity {

    EditText etTitle,etDes,etTime;
    Button btnSave,btnCancel;
    TextView tvTimeline;
    int TaskID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        etTitle = findViewById(R.id.titledoes);
        etDes = findViewById(R.id.etdescription);
        tvTimeline =findViewById(R.id.tvtimeline);


        btnSave = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        tvTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String des = etDes.getText().toString().trim();

                TasksDB db = new TasksDB(NewTask.this);
                db.open();
                db.entryTasks(title,des);
                // db.close();
                // db.open();
                int tID = db.getLatestTaskID();
                db.close();
                TaskID = tID;

                Toast.makeText(NewTask.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent timeline= new Intent(getApplicationContext(), Timeline.class);
                timeline.putExtra("TaskID",tID);
                startActivity(timeline);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //// this is just for now
                //// not stable
                TasksDB db = new TasksDB(NewTask.this);
                db.open();
                ArrayList<Timers> timers = db.getTimersDataOfATask(TaskID+"");

                if(timers.size()==0)
                {
                    Toast.makeText(NewTask.this, "You have to give a timeline", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NewTask.this, "Saved", Toast.LENGTH_SHORT).show();

                    finish();
                }


            }
        });


    }



}