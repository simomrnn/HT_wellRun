package com.example.ht_well;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static android.widget.Toast.makeText;

//Creating variables and strings. Strings include url and key for API
public class Weather extends AppCompatActivity {
    EditText locationInput;
    TextView weatherReport;
    private final String url = "http://api.openweathermap.org/data/2.5/weather";
    private final String appid = "d9bd982f3485d728d879e5b7c0fbfd24";


    //Luodaan yhteys XML tiedostossa oleviin käyttöliittymän osiin, ja kerrotaan missä ne sijaitsevat
    //xml layout connection to class
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationInput = findViewById(R.id.LocationInput);
        weatherReport = findViewById(R.id.WeatherReport);

        //Method, that gets us back to main menu
        configureBackToMenu();
    }

        //Config for button. Setting onclick listener and linking activities.
        //using finish() for exiting the application and allowing the android back button
        // to work as intended.
    private void configureBackToMenu() {
        Button backToMenu = (Button) findViewById(R.id.BackToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    // method for connecting to API,
    //takes input, sends request to interface with that information
    // shows message if no input is given
    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = locationInput.getText().toString().trim();
        if (city.equals("")) {
            weatherReport.setText("Void has no weather, give a proper location.");
        } else {
            tempUrl = url + "?q=" + city + "&units=metric&appid=" + appid;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {

            //if a response is provided from API, looks for specific information
            // provides a formatted output into text, for user to see
            // takes response as parameter
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                        String output = "";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp");
                            double feelslike = jsonObjectMain.getDouble("feels_like");
                            int humidity = jsonObjectMain.getInt("humidity");
                            String cityname = jsonResponse.getString("name");

                            output = output + "Current weather in " + cityname
                                    + '\n' + "Description: " + description + "."
                                    + '\n' + "Temp: " + temp + " C,"
                                    + '\n' + "Feels like: " + feelslike +  " C,"
                                    + '\n' + "Humidity: " + humidity;

                            weatherReport.setText(output);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
            }
        }, new Response.ErrorListener() {
            //shows toast message if no response is gotten
            // for example if city name is misspelled
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "something is not right", Toast.LENGTH_SHORT).show();

            }
        });
        //a request queue for inquiries to API
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}