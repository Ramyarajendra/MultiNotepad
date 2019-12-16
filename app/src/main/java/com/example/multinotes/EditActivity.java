package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//edit activity page
public class EditActivity extends AppCompatActivity {

    private EditText Title; //refers to the edit text view for text title in edit page
    private EditText content; //refers to notes edit text in edit page
    private Menu opmenu; //refers to menu
    private String prevtit = "" , prevcon = "";
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        content = findViewById(R.id.noteText); //refers to edit text notes
        Title = findViewById(R.id.noteTitle); //refers to edit text title
        Intent i = getIntent();
        if (i.hasExtra("TITLE")) {
            prevtit = i.getStringExtra("TITLE");
            Title.setText(prevtit);
        }
        if (i.hasExtra("NOTE")) {
            prevcon = i.getStringExtra("NOTE");
            content.setText(prevcon);
        }
        content.setMovementMethod(new ScrollingMovementMethod());//used to set scrolling movement
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //to invoke menu
        getMenuInflater().inflate(R.menu.save, menu);
        opmenu = menu;
        return true;
    }
    @Override
    public void onBackPressed() { //function for back button
        if(Title.getText().toString().isEmpty()){ //checks if text entered is empty and displays toast
            Toast.makeText(this, "Note was not saved", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        else if(prevtit.equals(Title.getText().toString()) && prevcon.equals(content.getText().toString())){
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //used to display alert dialog box
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { //on positive response
                public void onClick(DialogInterface dialog, int id) {
                    saveNote();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() { //for negative response
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setMessage("Do you want to save this Note?");
            builder.setTitle("Note Save");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: in optionsItemSelected");
        switch (item.getItemId()) {
            case R.id.save: //refers to save id in save.xml
                saveNote(); //calls savenote method
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveNote(){
        Notes n = new Notes(); //refers to object of notes class
        n.setNotesTitle(Title.getText().toString()); //used for setting title and content
        n.setNotesText(content.getText().toString());
        n.setLastUpdate(Calendar.getInstance().getTime().toString());
        Intent i = new Intent();
        i.putExtra("EDIT_NOTE", n);
        if(Title.getText().toString().isEmpty()) {
            i.putExtra("STATUS", "NO_CHANGE");
            Toast.makeText(this, "Empty Note Cannot Be Saved", Toast.LENGTH_SHORT).show();
        }
        else if(prevtit.isEmpty() && prevcon.isEmpty())
            i.putExtra("STATUS", "NEW");
        else if(prevtit.equals(Title.getText().toString()) && prevcon.equals(content.getText().toString()))
            i.putExtra("STATUS", "NO_CHANGE");
        else
            i.putExtra("STATUS", "CHANGE");
        setResult(RESULT_OK, i);
        finish();
    }
}