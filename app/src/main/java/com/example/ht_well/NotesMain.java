package com.example.ht_well;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesMain extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    NoteDatabase noteDatabase;


    //variables, list for all notes, recyclerview for listing entries
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notes);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);

        noteDatabase = new NoteDatabase(this);
        List<Note> allEntries = noteDatabase.getNotes();
        recyclerView = findViewById(R.id.entriesList);

        configureBackToMenu();

        //if there are no items in the list allEntries, will show a message.
        // If not empty message will be invisible.
        if(allEntries.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(allEntries);
        }

    }
    //configuring the use of androids own back button.
    private void configureBackToMenu() {
        Button toWeather = (Button) findViewById(R.id.ToMenuNotes);
        toWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesMain.this, MainMenu.class));
            }
        });
    }


    //method for listing entries in recyclerview
    private void displayList(List<Note> allEntries) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, allEntries, this);
        recyclerView.setAdapter(adapter);
    }

    //parameter: ID, starts activity detail
    //method called when entry is clicked, that is in recyclerview
    //accesses database to retrieve specific note clicked.
    public void onRecycleClick(int ID) {
        System.out.println(ID);
        List<Note> allEntries = noteDatabase.getNotes();
        Note selectedNote = allEntries.get(ID);
        Intent i = new Intent(NotesMain.this, Detail.class);
        i.putExtra("ID",selectedNote.getID());
        startActivity(i);
    }

    //menu items to display
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }


    //intent i.input extra, recycler viewhen sisäään id muuttujaan
    //muuttujasta noteid --> putextra --> intent
    // takes item: add_btn, starts activity .addnote
    //returns add_btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_btn) {
            Toast.makeText(this, "Add new entry", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(NotesMain.this, AddNote.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    //method for changing text visibility in notes main
    //checks if note database is empty
    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllEntries = noteDatabase.getNotes();
        if(getAllEntries.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllEntries);
        }

    }
}
