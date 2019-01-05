package com.example.ayush.newscrisp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int LOADER_ID = 1;
    private final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=";
    private static String country;
    NewsAdapter mAdapter = null;
    ArrayList<News> arrayList = new ArrayList<News>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.SETTINGS, MODE_PRIVATE);
        boolean z = preferences.getBoolean(SettingsActivity.OVERRIDDEN, false);
        if (z) {
            setContentView(R.layout.activity_main);
            setSupportActionBar((Toolbar) findViewById(R.id.MainActivityToolbar));
            country = preferences.getString(SettingsActivity.SELECTED, "default");
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                NewsQuery.getCountry(country);
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setOffscreenPageLimit(7);
                CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
            } else {
                Intent i = new Intent(MainActivity.this, NoInternet.class);
                startActivity(i);
            }
        } else {
            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.overflowSettings):
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
