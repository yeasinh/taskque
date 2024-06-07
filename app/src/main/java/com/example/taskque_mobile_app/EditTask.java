package com.example.taskque_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTask extends AppCompatActivity {

    EditText etTitle,etDes;
    TextView btnNotes,btnLinks,tvTimeline;
    int TaskID,TimersID;
    Button btnSet,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Bundle bundle = getIntent().getExtras();

         TaskID = bundle.getInt("TaskID");
         TimersID = bundle.getInt("TimersID");

       // Toast.makeText(this, TimersID+"", Toast.LENGTH_SHORT).show();
        etTitle = findViewById(R.id.textview_tasktitle);
        etDes = findViewById(R.id.textview_taskDescription);
        btnNotes = findViewById(R.id.textview_tasknotes);
        btnLinks = findViewById(R.id.textview_tasklinks);
        btnSet = findViewById(R.id.set_btn_edit_task);
        btnCancel = findViewById(R.id.cancle_btn_edit_task);
        tvTimeline = findViewById(R.id.textview_tasktimeline);

        TasksDB db = new TasksDB(this);
        db.open();
        Tasks t= db.getTasksData(TaskID+"");
        Timers timers = db.getATodayTimersData(TimersID+"");
        if(timers.getType()==null)
        {
            timers = db.getATimersData(TimersID+"");
        }
        db.close();

        etTitle.setText(t.getTitle());
        etDes.setText(t.getDescription());
        tvTimeline.setText(timers.getHourOFDay()+":"+timers.getMinute()+"-"+timers.getDayOFMonth()
                +"/"+(timers.getMonth()+1)+"/"+timers.getYear()+"-"+timers.getType());

        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notes= new Intent(getApplicationContext(), CreateNotes.class);
                notes.putExtra("TaskID",TaskID);
                startActivity(notes);
            }
        });

        btnLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent links= new Intent(getApplicationContext(), createLinks.class);
                links.putExtra("TaskID",TaskID);
                startActivity(links);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String des = etDes.getText().toString().trim();
                TasksDB db = new TasksDB(EditTask.this);
                db.open();

                db.updateTasksEntry(TaskID+"",title,des);
                db.close();
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

}