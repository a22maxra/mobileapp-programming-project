package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailView extends AppCompatActivity {
    private TextView detailsCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        detailsCar = findViewById(R.id.detailsCar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("details");
            detailsCar.setText(name);
        }
    }
}