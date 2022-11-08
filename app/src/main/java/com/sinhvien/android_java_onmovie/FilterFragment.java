package com.sinhvien.android_java_onmovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.model.Film;
import com.sinhvien.android_java_onmovie.model.Genre;

import java.util.ArrayList;

public class FilterFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener {

    TextView tvName;
    RecyclerView rvFilms;
    FilmAdapter adapter;
    ArrayList<Film> films;

    FirebaseDatabase fDB;
    DatabaseReference mDB;

    public String GERNE_ID;

    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GERNE_ID = getArguments().getString("genre_id");

        tvName = view.findViewById(R.id.tvName);
        rvFilms = view.findViewById(R.id.rvFilms);

        films = new ArrayList();

        fDB = FirebaseDatabase.getInstance();
        mDB = fDB.getReference();

        if(GERNE_ID == "all") {
            loadAllFilms();
        } else {
            loadFilterFilms();
        }

        adapter = new FilmAdapter(films,this,1);
        rvFilms.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvFilms.setLayoutManager(gridLayoutManager);

        tvName.setText(getArguments().getString("genre_name"));

    }

    private void loadAllFilms() {
        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemList : snapshot.getChildren()) {
                    Film item = itemList.getValue(Film.class);
                    films.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFilterFilms() {
        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemList : snapshot.getChildren()) {
                    Film item = itemList.getValue(Film.class);
                    for(int i = 0; i < item.getFilm_genres().size(); i++) {
                        String genre = item.getFilm_genres().get(i);

                        if(genre.equals(GERNE_ID)) {
                            films.add(item);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void OnFilmItemCLickListener(Film film) {
        Toast.makeText(getContext(), film.getName(), Toast.LENGTH_SHORT).show();
    }
}