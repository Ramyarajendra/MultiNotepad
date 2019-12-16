package com.example.multinotes;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView title; //title
    public TextView lastupdated; //lastupdated time
    public TextView notestext; //notes


    public ViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title); //refers to textview for title
        lastupdated = (TextView) view.findViewById(R.id.date_modified);//refers to textview for date
        notestext = (TextView) view.findViewById(R.id.note_text);//refers to textview for notes
    }
}
