package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener{

    private RecyclerViewAdapter adapter;
    private ArrayList<Car> items;
    private Gson gson = new Gson();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("SortingPrefs", Context.MODE_PRIVATE);
        editor = preferences.edit();

        adapter = new RecyclerViewAdapter(this, items, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Car item) {
                Intent intent = new Intent(MainActivity.this, DetailView.class);
                intent.putExtra("details", item.getCar());
                startActivity(intent);
            }
        });
        RecyclerView view = findViewById(R.id.displayCars);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        new JsonTask(this).execute("https://mobprog.webug.se/json-api?login=a22maxra");

        Button myButton = findViewById(R.id.buttonAbout);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to launch the about activity
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);

                // Launch the second activity
                startActivity(intent);
            }
        });

        Button buttonAsc = findViewById(R.id.buttonAsc);
        buttonAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortAsc();

                editor.putString("sortOrder", "ASC");
                editor.apply();
            }
        });

        Button buttonDesc = findViewById(R.id.buttonDesc);
        buttonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortDesc();

                editor.putString("sortOrder", "DESC");
                editor.apply();
            }
        });

        Button buttonReset = findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask(MainActivity.this).execute("https://mobprog.webug.se/json-api?login=a22maxra");

                editor.putString("sortOrder", "Default");
                editor.apply();
            }
        });
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
        Type type = new TypeToken<List<Car>>() {
        }.getType();
        items = gson.fromJson(json, type);

        adapter.setItems(items);
        adapter.notifyDataSetChanged();

        // Retrieve sorting preference
        String sortOrder = preferences.getString("sortOrder", "Default");

        if (sortOrder.equals("ASC")) {
            sortAsc();
        } else if (sortOrder.equals("DESC")) {
            sortDesc();
        }
    }

    public void sortAsc(){
        Collections.sort(items, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return car1.getName().compareTo(car2.getName());
            }
        });
        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();
    }

    public void sortDesc(){
        // Sort the items list by name descending
        Collections.sort(items, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return car2.getName().compareTo(car1.getName());
            }
        });

        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();
    }
}

