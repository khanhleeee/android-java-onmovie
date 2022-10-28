package com.sinhvien.android_java_onmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener {

    ViewPager2 viewPager2Slider;
    SliderViewPagerAdapter adapter;
    TabLayout tabLayout;

    RecyclerView rvMonthlyFilm;
    RecyclerView rvActionFilm;
    FilmAdapter adapterMonthlyFilm;

    List<Film> sliderItems;
    List<Film> monthlyFilms;

    FirebaseDatabase firebaseBD;
    DatabaseReference mDB;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2Slider = view.findViewById(R.id.slide_pager);
        sliderItems = new ArrayList<>();

        // Slider load from firebase
        loadSlider();

        adapter = new SliderViewPagerAdapter(sliderItems, getContext());
        viewPager2Slider.setAdapter(adapter);


        // Sliders tablayout
        tabLayout = view.findViewById(R.id.slider_tabLayout);
        setTabLayout();

        rvMonthlyFilm = view.findViewById(R.id.rvMonthlyFilms);
        monthlyFilms = new ArrayList<>();

        rvActionFilm = view.findViewById(R.id.rvActionFilms);

        // load monthly films
        loadMonthlyFilms();

        adapterMonthlyFilm = new FilmAdapter(monthlyFilms, this, 1);
        rvMonthlyFilm.setAdapter(adapterMonthlyFilm);
        rvActionFilm.setAdapter(adapterMonthlyFilm);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMonthlyFilm.setLayoutManager(linearLayoutManager);
        rvActionFilm.setLayoutManager(linearLayoutManager2);
    }

    // Methods
    private void setTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager2Slider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: break;
                    case 1: break;
                    case 2: break;
                }
            }
        }).attach();
    }

    private void loadSlider() {
        firebaseBD = FirebaseDatabase.getInstance();
        mDB = firebaseBD.getReference();

        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Film item = itemFilm.getValue(Film.class);
                    if(sliderItems.size() < 4) {
                        sliderItems.add(item);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMonthlyFilms() {
        firebaseBD = FirebaseDatabase.getInstance();
        mDB = firebaseBD.getReference();

        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Film item = itemFilm.getValue(Film.class);
                    monthlyFilms.add(item);
                }
                adapterMonthlyFilm.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void OnFilmItemCLickListener(Film film) {
        Bundle bundle = new Bundle();

        bundle.putString("backdrop", film.getBackdrop());
        bundle.putString("name", film.getName());
        bundle.putString("country", film.getCountry());
        bundle.putString("limitedAge", String.valueOf(film.getLimitedAge()));
        bundle.putString("desc", film.getDesc());

        Intent intent = new Intent(getContext(), MovieDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}