package com.example.taskque_mobile_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OverDueFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_over_due, container, false);

       // RecyclerView homeRecyclerView=(RecyclerView)view.findViewById(R.id.home_recycler_view);



      //  Task[] taskDetails={
          //      new Task("naanaa","no class bruh","11.00 AM"),

           //     new Task("Class","no class again","10.00 AM")
       // };

       // TaskAdapter taskAdapter=new TaskAdapter(taskDetails);
       // homeRecyclerView.setAdapter(taskAdapter);
        return view;
    }
}