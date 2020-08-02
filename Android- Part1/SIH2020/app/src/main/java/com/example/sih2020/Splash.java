package com.example.sih2020;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {
    TextView GetAWay;

    //Animation
    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //hooks
        GetAWay= findViewById (R.id.textView);

        //Animation

        bottomAnim=AnimationUtils.loadAnimation (this,R.anim.bottom_anim);

//set Animation on elements
        GetAWay.setAnimation (bottomAnim);

        Thread thread=new Thread (new Runnable ( ) {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent=new Intent (Splash.this,OnBoarding.class);
                    startActivity (intent);
                } catch (InterruptedException e) {
                    e.printStackTrace ( );
                }
            }
        });
        thread.start ();
    }
}