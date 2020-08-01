package com.example.trafficcontroller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import  android.net.Uri;

import android.os.Bundle;
import android.widget.Button;

public class MapActivity extends AppCompatActivity {
Button btnmap;
String uri="geo:0,0?q=india";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
      btnmap=(Button)findViewById(R.id.btndemo);   }
}