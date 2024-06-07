package com.example.taskque_mobile_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClicked{


    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pendingBtn,overdueBtn;
        pendingBtn=findViewById(R.id.pending_button);
        overdueBtn=findViewById(R.id.overdue_button);

        //setFragment(new PendingFragment());



        TasksDB db = new TasksDB(this);
        db.open();
        ArrayList<Timers> t =  db.getTimersData();


        db.close();

        for(int i=0;i<t.size();i++)
        {
            ApplicationClass.pendingList.add(t.get(i));
        }
        recyclerView = findViewById(R.id.rc_home);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new TaskAdapter(MainActivity.this,ApplicationClass.pendingList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();




        pendingBtn.setOnClickListener(new View.OnClickListener() {   //home page fragment
            @Override
            public void onClick(View v) {




                //replaceFragment(new PendingFragment());
                    
                    
                

            }
        });

        overdueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent overDue= new Intent(getApplicationContext(), OverDueTaskActivity.class);
                startActivity(overDue);
               // replaceFragment(new OverDueFragment());




            }
        });




        TextView dateView=findViewById(R.id.textview_date);
        //TextView timeview=findViewById(R.id.textview_time);





        FloatingActionButton createButton=findViewById(R.id.activity_main_floatingbutton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(MainActivity.this, "yemete kudasai", Toast.LENGTH_LONG).show();*/
                Intent buttonNewTask= new Intent(getApplicationContext(), NewTask.class);
                startActivity(buttonNewTask);

            }
        });



        //showDateTime(dateView);
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("EEEE, dd-MM-yyyy");
       // SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("hh:mm a");

        String date=simpleDateFormat1.format(calender.getTime());
       // String time=simpleDateFormat2.format(calender.getTime());



        dateView.setText(date);
       // timeview.setText(time);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,  0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            //have to delete the alarm by request code remember
            //for now it will work
            //have to update the whole database
            //so be care full

            int timerID = ApplicationClass.pendingList.get(fromPosition).getTimersID();
            int taskID = ApplicationClass.pendingList.get(fromPosition).getTaskID();
            TasksDB db = new TasksDB(MainActivity.this);
            db.open();
            db.deleteTimersEntry(timerID+"");
            db.close();
            Intent timeline= new Intent(getApplicationContext(), Timeline.class);
            timeline.putExtra("TaskID",taskID);
            startActivity(timeline);



            //Collections.swap(ApplicationClass.pendingList,fromPosition,toPosition);
            //recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //used to handle left and right swipe
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();

        TasksDB db = new TasksDB(this);
        db.open();
        ArrayList<Timers>t = db.getTimersData();
        db.close();
        ApplicationClass.pendingList.clear();
        for(int i=0;i<t.size();i++)
        {
            ApplicationClass.pendingList.add(t.get(i));
        }
        myAdapter.notifyDataSetChanged();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();  //to set the default fragment
        fragmentManager.beginTransaction()
                .replace(R.id.recyclerview_holder, fragment)
                .commit();
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.recyclerview_holder,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onItemClicked(int index) {
        //call the Edit Task activity
        Intent editTask= new Intent(getApplicationContext(), EditTask.class);
        editTask.putExtra("TaskID",ApplicationClass.pendingList.get(index).getTaskID());
        editTask.putExtra("TimersID",ApplicationClass.pendingList.get(index).getTimersID());
        startActivity(editTask);

    }
}