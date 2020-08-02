package com.example.sih2020;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sih2020.HelperClasses.FeaturedAdapter;
import com.example.sih2020.HelperClasses.FeaturedHelperClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        featuredRecycler=findViewById(R.id.featured_recycler);
        featuredRecycler();

    }
    private void featuredRecycler(){
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.imagedash,"SNOWBALL","organize important notification " +
                "right from the  notification panel"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.imagedash1,"KAAVALAN SOS","organize important notification " +
                "right from the  notification panel"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.imagedash2,"WAZE","organize important notification " +
                "right from the  notification panel"));

        adapter =new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

        GradientDrawable gradient1=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new  int[]{0xffeff400,0xffaff600});
    }

}