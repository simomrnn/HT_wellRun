package com.example.ht_well;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.TimeZone;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar cal;
    String currentDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Log Entry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = findViewById(R.id.EntryTitleEdit);
        noteDetails = findViewById(R.id.noteDetail);

        //textchangedlistener will allow us to show typed text in the toolbar.
        //a text changedlistener for set the title. There were some problems, and this over complicated
        //listener allow the app to work as intended
        noteTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        getSupportActionBar().setTitle(s);
                    }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Get the current date and time for log entry. d/m/y & h/m
        cal = Calendar.getInstance();
        currentDate = cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get((Calendar.MONTH)+1) +"." + cal.get(Calendar.YEAR);
        currentTime = pad(cal.get(Calendar.HOUR_OF_DAY)+3) + "." + pad(cal.get(Calendar.MINUTE));

        Log.d("calendar", "Date and Time: " + currentDate + " and " + currentTime);
    }
    // Method for displaying 0 in front of hour and minute, if value is under 10
    // returns a formatted time for nicer UI
    private String pad(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);
    }

    //savemanu xlm file is inflated to be seen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }


    //item.getitem adds functions to buttons that have been added, save and delete.
    //save note can now access note information, timestamps and details
    //adds the note to the notedatabase. onbackpressed returns to the main window after action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Delete) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
            if(item.getItemId() == R.id.Save) {
                Note entry = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), currentDate, currentTime);
                NoteDatabase db = new NoteDatabase(AddNote.this);
                db.addNote(entry);
                Toast.makeText(this, "Log saved", Toast.LENGTH_SHORT).show();
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}