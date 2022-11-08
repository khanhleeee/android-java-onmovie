package com.sinhvien.android_java_onmovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.model.Genre;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    FirebaseDatabase fDB;
    DatabaseReference mDB;

    FilterAdapter filterAdapter;
    ArrayList<Genre> genreList;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoviesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
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
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fDB = FirebaseDatabase.getInstance();
        mDB = fDB.getReference();

        genreList = new ArrayList<>();
        genreList.add(new Genre("all", "Tất cả"));

        mDB.child("genres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()) {
                    Genre genre = item.getValue(Genre.class);
                    genreList.add(genre);
                }
                filterAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabLayout = view.findViewById(R.id.movie_tabLayout);
        viewPager2 = view.findViewById(R.id.movie_viewPager);

        filterAdapter = new FilterAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), genreList);
        viewPager2.setAdapter(filterAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(genreList.get(position).getName());
        }).attach();
    }
}