package com.sinhvien.android_java_onmovie.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sinhvien.android_java_onmovie.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    ImageView logo, imgPopcorn, imgFilmPopcorn;
    Animation topAnimation, bottomAnimation;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Image
        logo = findViewById(R.id.welcomeLogo);
        imgPopcorn = findViewById(R.id.welcomePopcorn);
        imgFilmPopcorn = findViewById(R.id.welcomeFilmPopcorn);

        logo.setAnimation(topAnimation);
        imgFilmPopcorn.setAnimation(bottomAnimation);
        imgPopcorn.setAnimation(bottomAnimation);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intentWelcome_Login = new Intent(WelcomeActivity.this, IntroActivity.class);
                startActivity(intentWelcome_Login);
            }
        }, 5000);

    }
}