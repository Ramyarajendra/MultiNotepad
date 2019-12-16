package com.example.multinotes;

import android.os.AsyncTask;
import android.util.JsonReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {



    private MainActivity mAct; //refers to main activity
    public MyAsyncTask(MainActivity ma) { //constructor
        mAct = ma;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            InputStream input = mAct.getApplicationContext().openFileInput(mAct.getString(R.string.file_name)); //file input stream
            JsonReader reader = new JsonReader(new InputStreamReader(input, mAct.getString(R.string.encoding)));
            reader.beginObject(); //reads the file as json
            String read;
            while (reader.hasNext()) {
                read = reader.nextName();
                if (read.equals("notes")) { //checks for notes
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Notes tempNotes = new Notes();
                        reader.beginObject();
                        while(reader.hasNext()) {
                            read = reader.nextName();
                            if (read.equals("title")) { //checks for title
                                tempNotes.setNotesTitle(reader.nextString());
                            } else if (read.equals("timestamp")) { //checks for last updated date
                                tempNotes.setLastUpdate(reader.nextString());
                            } else if (read.equals("note")) {
                                tempNotes.setNotesText(reader.nextString());
                            } else {
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                        mAct.getNotesList().add(tempNotes);

                    }
                    reader.endArray();
                }
                else{
                    reader.skipValue();
                }

            }
            reader.endObject();

        }catch (FileNotFoundException e) { //handles file not found exception
            e.printStackTrace();
        } catch (Exception e) { //handles other exceptions
            e.printStackTrace();
            System.out.println("Error: "+e);
        }
        return null;
    }

}

