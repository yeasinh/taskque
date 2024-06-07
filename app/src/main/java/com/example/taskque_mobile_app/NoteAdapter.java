package com.example.taskque_mobile_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Notes> notes;
    ItemClicked activity;


    public interface ItemClicked
    {
        void onItemClicked(int index);
    }
    public NoteAdapter(Context context,ArrayList<Notes> list) {
        notes =list;
       activity = (ItemClicked) context;

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }




    @NonNull

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notesaandlinks,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        holder.itemView.setTag(notes.get(position));
        holder.note.setText(notes.get(position).getTitle());
        holder.des.setText(notes.get(position).getDescription());



    }




    public class NoteViewHolder extends RecyclerView.ViewHolder{


        TextView note,des;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.tasknotesTitle_cardview);
            des=itemView.findViewById(R.id.tasknoteDescription_cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when clicked
                    activity.onItemClicked(notes.indexOf((Notes) v.getTag()));
                }
            });
        }

    }
}
