package com.example.taskque_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class createLinks extends AppCompatActivity implements LinkAdapter.ItemClicked {

    int TaskID;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Links> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_links);

        Bundle bundle = getIntent().getExtras();

        TaskID = bundle.getInt("TaskID");

        TasksDB db = new TasksDB(this);
        db.open();
         list = db.getLinksData(TaskID+"");
        db.close();


        recyclerView = findViewById(R.id.link_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new LinkAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        FloatingActionButton create = findViewById(R.id.link_floatingbutton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(-1);

            }
        });


    }

    public void showCustomDialog(int index)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_notes);

        final EditText title = dialog.findViewById(R.id.edit_note_title);
        final EditText des = dialog.findViewById(R.id.edit_note_description);
        final Button btnSave = dialog.findViewById(R.id.btnDialogSave);
        final Button btnCancel = dialog.findViewById(R.id.btnDialogCancel);

        if(index==-1)
        {
            CharSequence c = "Title";
            title.setHint(c);
            CharSequence d = "Link";
            des.setHint(d);
        }
        else
        {
            title.setText(list.get(index).getTitle());
            des.setText(list.get(index).getLink());
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tt = title.getText().toString().trim();
                String dd = des.getText().toString().trim();

                if(index==-1)
                {
                    if(!tt.isEmpty() && !dd.isEmpty())
                    {
                        TasksDB db = new TasksDB(createLinks.this);
                        db.open();
                        db.entryLinks(TaskID,tt,dd);
                        db.close();

                        int nid=0;
                        if(list.size()!=0)
                        {
                            nid = list.size() -1;
                            nid = list.get(nid).getLinksID();
                        }


                        nid++;
                        list.add(new Links(nid,TaskID,tt,dd));
                        myAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(createLinks.this, "Fill up the form", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if(!tt.isEmpty() && !dd.isEmpty())
                    {
                        TasksDB db = new TasksDB(createLinks.this);
                        db.open();
                        db.updateLinksEntry(list.get(index).getLinksID()+"",TaskID,tt,dd);
                        db.close();

                        list.get(index).setTitle(tt);
                        list.get(index).setLink(dd);
                        myAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(createLinks.this, "Fill up the form", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClicked(int index) {
        showCustomDialog(index);
    }
}