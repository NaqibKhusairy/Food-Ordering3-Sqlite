package com.naqib.food_ordering3_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button order, history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        order = findViewById(R.id.btnorder);
        history = findViewById(R.id.btnhistory);

        order.setOnClickListener(view -> {
            Intent x = new Intent(getApplicationContext(),OrderActivity.class);
            startActivity(x);
        });

        history.setOnClickListener(view -> {
            Intent x = new Intent(getApplicationContext(),HistoryActivity.class);
            startActivity(x);
        });
    }
}