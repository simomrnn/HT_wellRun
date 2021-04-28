package com.example.ht_well;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText entryTitle;
    EditText entryContent;
    Calendar cal;
    String currentDate;
    String currentTime;
    long entryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //creates variables, needed for accessing database and ID
        Intent i = getIntent();
        entryID = i.getLongExtra("ID",0);
        NoteDatabase db = new NoteDatabase(this);
        Note note = db.getNote(entryID);

        //Text watcher is needed for accessing items, other methods could work as well, but this worked
        // calls for note class methods to get information.

        final String title = note.getTitle();
        String content = note.getContent();
        entryTitle = findViewById(R.id.EntryTitleEdit);
        entryContent = findViewById(R.id.EntryDetailsEdit);
        entryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        entryTitle.setText(title);
        entryContent.setText(content);

        // set current date and time using calendar utility
        // problems occured: wrong timezone and month showing up wrong.
        // Timezone fixed by +3, setting timezone exists, but this works as well.
        //month is still a mystery????
        cal = Calendar.getInstance();
        currentDate = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: " + currentDate);
        currentTime = pad(cal.get(Calendar.HOUR)) + ":" + pad(cal.get(Calendar.MINUTE));
        Log.d("TIME", "Time: " + currentTime);
    }

    // just a method for making time look nicer in ui, adds a 0 before time if consist of 1 number
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    //Method gets savemenu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }


    //option menu method for notes
    //takes a note as parameter
    //accesses database to retrieve note,
    //method gets note from database
    //menu items used: save and cancel edit
    //returns method on option menu selected, with note
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Save){
            Note note = new Note(entryID,entryTitle.getText().toString(),entryContent.getText().toString(),currentTime,currentTime);
            NoteDatabase sDB = new NoteDatabase(getApplicationContext());
            long id = sDB.editNote(note);
            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.Delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(Edit.this,NotesMain.class);
        startActivity(i);
    }


}