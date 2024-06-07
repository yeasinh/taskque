package com.example.taskque_mobile_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private ArrayList<Links> Links;
    ItemClicked activity;


    public interface ItemClicked
    {
        void onItemClicked(int index);
    }
    public LinkAdapter(Context context, ArrayList<Links> list) {
        Links =list;
        activity = (ItemClicked) context;

    }

    @Override
    public int getItemCount() {
        return Links.size();
    }




    @NonNull

    @Override
    public LinkAdapter.LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notesaandlinks,parent,false);
        return new LinkAdapter.LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkAdapter.LinkViewHolder holder, int position) {
        holder.itemView.setTag(Links.get(position));
        holder.Link.setText(Links.get(position).getTitle());
        holder.des.setText(Links.get(position).getLink());



    }




    public class LinkViewHolder extends RecyclerView.ViewHolder{


        TextView Link,des;


        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            Link=itemView.findViewById(R.id.tasknotesTitle_cardview);
            des=itemView.findViewById(R.id.tasknoteDescription_cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when clicked
                    activity.onItemClicked(Links.indexOf((Links) v.getTag()));
                }
            });
        }

    }
}
