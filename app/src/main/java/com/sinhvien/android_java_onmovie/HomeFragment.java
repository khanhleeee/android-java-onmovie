package com.sinhvien.android_java_onmovie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.adapter.SliderViewPagerAdapter;
import com.sinhvien.android_java_onmovie.model.Film;

import org.checkerframework.checker.units.qual.A;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener {

    ViewPager2 viewPager2Slider;
    SliderViewPagerAdapter adapter;
    TabLayout tabLayout;

    RecyclerView rvMonthlyFilm;
    RecyclerView rvActionFilm;
    RecyclerView rvNoNameFilm;

    FilmAdapter adapterMonthlyFilm;
    FilmAdapter adapterActionFilm;
    FilmAdapter adapterNoNameFilm;

    List<Film> sliderItems;
    List<Film> yearlyFilms;
    List<Film> actionFilms;
    List<Film> animationFilms;

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

        firebaseBD = FirebaseDatabase.getInstance();
        mDB = firebaseBD.getReference();

        viewPager2Slider = view.findViewById(R.id.slide_pager);
        sliderItems = new ArrayList<>();

        // Slider load from firebase
        loadSlider();

        adapter = new SliderViewPagerAdapter(sliderItems, getContext());
        viewPager2Slider.setAdapter(adapter);

        // Sliders tablayout
        tabLayout = view.findViewById(R.id.slider_tabLayout);
        setTabLayout();

        rvMonthlyFilm = view.findViewById(R.id.rvYearlyFilm);
        rvActionFilm = view.findViewById(R.id.rvActionFilms);
        rvNoNameFilm = view.findViewById(R.id.rvAnimation);
        yearlyFilms = new ArrayList<>();
        actionFilms = new ArrayList<>();
        animationFilms = new ArrayList<>();


        // load action films
        loadActionFilms();
        loadMonthlyFilms();
        loadNoFilms();

        adapterMonthlyFilm = new FilmAdapter(yearlyFilms, this, 1);
        adapterActionFilm = new FilmAdapter(actionFilms, this, 1);
        adapterNoNameFilm = new FilmAdapter(animationFilms, this, 1);

        rvMonthlyFilm.setAdapter(adapterMonthlyFilm);
        rvActionFilm.setAdapter(adapterActionFilm);
        rvNoNameFilm.setAdapter(adapterNoNameFilm);

        LinearLayoutManager monthlyLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager actionLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager nonameLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvMonthlyFilm.setLayoutManager(monthlyLayout);
        rvActionFilm.setLayoutManager(actionLayout);
        rvNoNameFilm.setLayoutManager(nonameLayout);
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
        Query query = mDB.child("films").orderByChild("year");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Log.d("TAG", "aa: " + itemFilm);
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

    private void loadActionFilms() {
        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Film item = itemFilm.getValue(Film.class);

                    for(int i = 0; i < item.getFilm_genres().size(); i++) {
                        String genre = item.getFilm_genres().get(i);
                        if(genre.equals("hanhdong")) {
                            actionFilms.add(item);
                        }
                    }
                }
                adapterActionFilm.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMonthlyFilms() {
        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Film item = itemFilm.getValue(Film.class);
                    if(item.getYear() == Year.now().getValue()) {
                        yearlyFilms.add(item);
                    }
                }
                adapterMonthlyFilm.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNoFilms() {
        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemFilm: snapshot.getChildren()) {
                    Film item = itemFilm.getValue(Film.class);
                    for(int i = 0; i < item.getFilm_genres().size(); i++) {
                        String genre = item.getFilm_genres().get(i);
                        if(genre.equals("hoathinh")) {
                            animationFilms.add(item);
                        }
                    }

                }
                adapterNoNameFilm.notifyDataSetChanged();
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

        bundle.putString("id", film.getId());
        bundle.putString("backdrop", film.getBackdrop());
        bundle.putString("name", film.getName());
        bundle.putString("country", film.getCountry());
        bundle.putString("limitedAge", String.valueOf(film.getLimitedAge()));
        bundle.putString("desc", film.getDesc());

        ArrayList videos = new ArrayList(film.getVideos());
        bundle.putStringArrayList("videos", videos);

        ArrayList trailers = new ArrayList(film.getTrailers());
        bundle.putStringArrayList("trailers", trailers);

        ArrayList genres = new ArrayList(film.getFilm_genres());
        bundle.putStringArrayList("genres", genres);

        ArrayList film_casts = new ArrayList(film.getFilm_casts());
        bundle.putStringArrayList("cast", film_casts);

        Intent intent = new Intent(getContext(), MovieDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}