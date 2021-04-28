package com.example.ht_well;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

//very simple class, for main menu
//configures buttons to deifferent parts of app
//sets up layout.

public class MainMenu extends AppCompatActivity {

    // set layout, new methods for button to take to activities.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.main_menu);

        configureToWeather();
        configureToLog();
        }

        //Configuring buttons to start new activities when clicked.
        private void configureToWeather(){
        Button toWeather= (Button) findViewById(R.id.ToWeather);
        toWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Weather.class));
            }
        });
    }
        private void configureToLog(){
        Button toLog = (Button) findViewById(R.id.ToLog);
        toLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, NotesMain.class));
            }
        });

    }



}
