package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener{

    private RecyclerViewAdapter adapter;
    private ArrayList<Car> items;
    private Gson gson = new Gson();
    //private final String JSON_URL = "https://mobprog.webug.se/json-api?login=a22maxra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new RecyclerViewAdapter(this, items, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Car item) {
                Toast.makeText(MainActivity.this, item.getCar(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView view = findViewById(R.id.displayCars);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        new JsonTask(this).execute("https://mobprog.webug.se/json-api?login=a22maxra");
    }
    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
        Type type = new TypeToken<List<Car>>() {
        }.getType();
        items = gson.fromJson(json, type);

        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }
}
