package com.applicationmobile.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.applicationmobile.gridview.Adapter.GridAdapter;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id", 0);
        GridAdapter iamgeAdapter = new GridAdapter(this);
        ImageView imageView = findViewById(R.id.content_view);
        imageView.setImageResource(iamgeAdapter.thumbnail[position]);
    }
}