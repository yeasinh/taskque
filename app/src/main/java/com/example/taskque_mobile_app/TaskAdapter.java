package com.example.taskque_mobile_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Timers> tasks;
    ItemClicked activity;
    Context c;


    public interface ItemClicked
    {
        void onItemClicked(int index);
    }
    public TaskAdapter(Context context,ArrayList<Timers> list) {

        tasks = list;
       activity = (ItemClicked) context;
        c = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dailytask,parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.itemView.setTag(tasks.get(position));

        String time = tasks.get(position).getHourOFDay()+":"+tasks.get(position).getMinute()+"-"+tasks.get(position).getDayOFMonth()
                +"/"+(tasks.get(position).getMonth()+1)+"/"+tasks.get(position).getYear()+"-"+tasks.get(position).getType();
        holder.taskTime.setText(time);
        TasksDB db = new TasksDB(c);
        db.open();
        Tasks t = db.getTasksData(tasks.get(position).getTaskID()+"");
        db.close();

        String title = t.getTitle()+"";
        holder.taskTitle.setText(title);
        holder.taskDescription.setText(t.getDescription());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle,taskDescription,taskTime;


        @SuppressLint("CutPasteId")
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle=itemView.findViewById(R.id.tasktitle_cardview);
            taskDescription=itemView.findViewById(R.id.taskdescription_cardview);
            taskTime=itemView.findViewById(R.id.tasktime_cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when clicked
                    activity.onItemClicked(tasks.indexOf((Timers)v.getTag()));
                }
            });
        }




    }




}

