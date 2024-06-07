package com.example.taskque_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class OverDueTaskActivity extends AppCompatActivity implements TaskAdapter.ItemClicked{

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Timers> overDueList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_due_task);

        TasksDB db = new TasksDB(this);
        db.open();
        overDueList = db.getTodayTimersData();

        db.close();

        recyclerView = findViewById(R.id.rec_overdue);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(OverDueTaskActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new TaskAdapter(OverDueTaskActivity.this,overDueList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {
        Intent editTask= new Intent(getApplicationContext(), EditTask.class);
        editTask.putExtra("TaskID",overDueList.get(index).getTaskID());
        editTask.putExtra("TimersID",overDueList.get(index).getTimersID());
        startActivity(editTask);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        TasksDB db = new TasksDB(this);
        db.open();
        ArrayList<Timers>t = db.getTodayTimersData();
        db.close();
        overDueList.clear();
        for(int i=0;i<t.size();i++)
        {
            overDueList.add(t.get(i));
        }
        myAdapter.notifyDataSetChanged();
    }
}