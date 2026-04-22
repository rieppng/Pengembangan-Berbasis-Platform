package com.applicationmobile.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.applicationmobile.gridview.Adapter.GridAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter(this));
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            i.putExtra("id", position);
            startActivity(i);
        });
    }


}