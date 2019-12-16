package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.JsonWriter;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//main activity page
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private List<Notes> nList = new ArrayList<>(); //list of notes
    private static final int CODE = 100;
    private int pos;
    private NotesAdapter Adap; //refers to notesadapter class
    private RecyclerView recyclerView; //refers to recycler view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Adap = new NotesAdapter(nList, this);
        recyclerView = findViewById(R.id.recyclerNotes);//refers to recycler view
        recyclerView.setAdapter(Adap); //used to set adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new MyAsyncTask(this).execute(); //executes async task
    }

    @Override
    public void onClick(View v) { //onclick functionality

        pos = recyclerView.getChildLayoutPosition(v); //gets position from recycler view
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        i.putExtra("TITLE", nList.get(pos).getNotesTitle());
        i.putExtra("DATE", nList.get(pos).getLastUpdate());
        i.putExtra("NOTE", nList.get(pos).getNotesText());
        startActivityForResult(i, CODE);
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        pos = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { //on positive response
                nList.remove(pos);
                Adap.notifyDataSetChanged();
                pos = -1;
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pos = -1;
            }
        });// on negative response
        builder.setMessage("Do you want to delete this note?"); // pops a dialog box with the message
        builder.setTitle("Delete");
        AlertDialog d = builder.create();
        d.show();
        return false;
    }
    @Override
    protected void onPause() {
        saveNotes(); //calls savenotes on pause
        super.onPause();
    }
    @Override
    protected void onResume(){
        nList.size();
        super.onResume();
        Adap.notifyDataSetChanged();
    }



    private void saveNotes() {

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            //opens file using filestream object
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();//Json writer object to write content
            writer.name("notes");
            writeNotesArray(writer);
            writer.endObject();
            writer.close();
        } catch (Exception e) { // used to handle exception
            e.getStackTrace();
        }
    }

    public void writeNotesArray(JsonWriter writer) throws IOException {
        writer.beginArray(); //for writing array , checks for io exception
        for (Notes value : nList) {
            writeNotesObject(writer, value);
        }
        writer.endArray();
    }

    public void writeNotesObject(JsonWriter writer, Notes val) throws IOException{
        writer.beginObject();
        writer.name("title").value(val.getNotesTitle());
        writer.name("timestamp").value(val.getLastUpdate());
        writer.name("note").value(val.getNotesText());
        writer.endObject();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info: //for info button

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.add: //for add button

                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent1, CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE) { //checks for status
            if (resultCode == RESULT_OK) {
                Notes edit_note = (Notes) data.getExtras().getSerializable("EDIT_NOTE");
                String status = data.getStringExtra("STATUS");
                if(status.equals("NO_CHANGE")){ } //checks for modifications
                else if(status.equals("CHANGE")){
                    nList.remove(pos);
                    nList.add(0, edit_note);
                }
                else if(status.equals("NEW")){
                    nList.add(0, edit_note);
                }
            }
        }
    }

    public List<Notes> getNotesList() {
        return nList;
    } // returns the list

}