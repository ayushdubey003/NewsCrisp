package com.example.ayush.newscrisp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toolbar;

import java.lang.reflect.Array;

public class SettingsActivity extends AppCompatActivity {
    public static final String SETTINGS = "Settings";
    public static final String OVERRIDDEN = "Overriden";
    public static final String SELECTED = "Country";
    private static String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.settingsToolbar));
        SharedPreferences mPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        final SharedPreferences.Editor edit = mPreferences.edit();
        Spinner spinner = (Spinner) findViewById(R.id.countrySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = adapterView.getItemAtPosition(i).toString();
                selection = s;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button button = (Button) findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putBoolean(OVERRIDDEN, true);
                SharedPreferences preferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);
                String s = preferences.getString(SELECTED, "default");
                edit.putString(SELECTED, selection);
                edit.apply();
                if (s.equals(selection))
                    finish();
                else {
                    Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        spinner.setAdapter(arrayAdapter);
    }
}