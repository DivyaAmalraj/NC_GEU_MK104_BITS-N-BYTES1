package com.example.mapway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btnmap;
    String uri="geo:0,0?q=india";
    Button btnmapsearch;
    EditText edtsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmap = (Button) findViewById(R.id.btndemo);
        btnmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Uri gmmIntentUri =Uri.parse(uri);
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                }
            }

        });

        btnmapsearch = (Button)findViewById(R.id.btnmapsearch);
        edtsearch = (EditText)findViewById(R.id.edtsearch);
        btnmapsearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Uri gmmIntentUri = Uri.parse("geo:o,o?q="+edtsearch.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }
}