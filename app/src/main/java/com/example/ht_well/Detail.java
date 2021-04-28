package com.example.ht_well;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Detail extends AppCompatActivity {
    long ID;


    //Create all variables needed
    //call for layout
    //set scrolling movement method for layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        ID = i.getLongExtra("ID",0);
        System.out.println(ID);
        NoteDatabase db = new NoteDatabase(this);
        Note note = db.getNote(ID);
        getSupportActionBar().setTitle(note.getTitle());
        TextView details = findViewById(R.id.entryDescDetail);
        details.setText(note.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            //calls for notedatabase, and retrives item with id to be deleted
            @Override
            public void onClick(View view) {
                NoteDatabase db = new NoteDatabase(getApplicationContext());
                db.deleteNote(ID);
                Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Inflate menuitems to be seen in app.
    //method takes menu item as parameter
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    //Gets item id, takes item as parameter and starts a new activity.
    //returns item that was selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit){
            Intent i = new Intent(Detail.this,Edit.class);
            i.putExtra("ID",ID);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    //allows back button to be used
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //method allows button to start mainmaenu activity --> takes user back to main menu
    private void goToMain() {
        Intent i = new Intent(Detail.this, NotesMain.class);
        startActivity(i);
    }
}