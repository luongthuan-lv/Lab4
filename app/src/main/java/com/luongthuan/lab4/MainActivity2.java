package com.luongthuan.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class MainActivity2 extends AppCompatActivity {
    Bundle bundle;
    TextView tvTitle, tvPath;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvPath = findViewById(R.id.tvPath);
        tvTitle = findViewById(R.id.tvTitle);
        img = findViewById(R.id.img);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        String title = bundle.getString("title");
        String url = bundle.getString("url");
        String path = bundle.getString("path");
        tvTitle.setText(title);
        tvPath.setText(path);
        Glide.with(this).load(url).into(img);
    }
}