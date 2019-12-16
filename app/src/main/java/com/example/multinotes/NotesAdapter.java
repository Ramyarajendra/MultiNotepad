package com.example.multinotes;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Notes> nList; //notes List
    private MainActivity maAct; //refers to main activity

    public NotesAdapter(List<Notes> notesList, MainActivity mainAct) {
        this.nList = notesList; //constructor to initialize
        this.maAct = mainAct;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, int pos) {
        Notes note = nList.get(pos); //gets the position from notes
        myViewHolder.title.setText(note.getNotesTitle()); //sets title
        myViewHolder.lastupdated.setText(note.getLastUpdate());//sets time
        if(note.getNotesText().length() > 80){
            String nNote = note.getNotesText().substring(0, 79);
            nNote = nNote.concat("...");
            myViewHolder.notestext.setText(nNote); //sets note
        }
        else {
            myViewHolder.notestext.setText(note.getNotesText());
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) { //invoked when view holder is called
        View iV = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noteslist, viewGroup, false);
        iV.setOnClickListener(maAct);
        iV.setOnLongClickListener(maAct);
        return new ViewHolder(iV);
    }
    @Override
    public int getItemCount() {//used for counting the number of notes present
        maAct.getSupportActionBar().setTitle("Notes "+"("+nList.size()+")");
        return nList.size();
    }
}

