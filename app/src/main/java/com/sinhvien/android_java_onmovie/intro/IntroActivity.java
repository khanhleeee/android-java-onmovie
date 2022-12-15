package com.sinhvien.android_java_onmovie.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sinhvien.android_java_onmovie.MainActivity;
import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.adapter.IntroViewPagerAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabLayout;
    Button btnStart;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mAuth = FirebaseAuth.getInstance();

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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if(user1 != null){
            Intent intent=new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else {

        }

    }
}