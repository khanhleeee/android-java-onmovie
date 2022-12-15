package com.sinhvien.android_java_onmovie;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    RelativeLayout header;

    TabLayout menuTablayout;
    ImageView ic_search, logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = findViewById(R.id.home_header);
        broadcastReceiver = new BroadcastReceiver();

        ic_search = findViewById(R.id.ic_search);
        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentToSearchActivity);
            }
        });

        logo = findViewById(R.id.logo);

        menuTablayout = findViewById(R.id.main_tabLayout);
        setFragment(0);
        menuTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setFragment(int position) {
        Fragment fragment = null;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (position) {
            case 0:
                ic_search.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                header.setLayoutParams(lp);
                fragment = new HomeFragment();
                break;
            case 1:
                ic_search.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                fragment = new MoviesFragment();
                header.setLayoutParams(lp);
                break;
            case 2:
                ic_search.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.INVISIBLE);
                lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -10);
                header.setLayoutParams(lp);
                fragment = new UserFragment();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}