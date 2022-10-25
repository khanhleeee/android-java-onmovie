package com.sinhvien.android_java_onmovie.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.adapter.IntroViewPagerAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabLayout;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        //setup Viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(IntroActivity.this);
        screenPager.setAdapter(introViewPagerAdapter);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentIntro = new Intent(IntroActivity.this, SignInActivity.class);
                startActivity(intentIntro);
            }
        });
        tabLayout = findViewById(R.id.intro_tabLayout);
        new TabLayoutMediator(tabLayout, screenPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        }).attach();

    }
}