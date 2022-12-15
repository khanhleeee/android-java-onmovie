package com.sinhvien.android_java_onmovie;

import static com.sinhvien.android_java_onmovie.R.drawable.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.adapter.SliderViewPagerAdapter;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    TabLayout menuTablayout;
    ImageView ic_search, logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver();

        logo = findViewById(R.id.logo);

        ic_search = findViewById(R.id.ic_search);
        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentToSearchActivity);
            }
        });
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

        switch (position) {
            case 0:
                ic_search.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                fragment = new HomeFragment();
                break;
            case 1:
                ic_search.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                fragment = new MoviesFragment();
                break;
            case 2:
                ic_search.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.INVISIBLE);
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