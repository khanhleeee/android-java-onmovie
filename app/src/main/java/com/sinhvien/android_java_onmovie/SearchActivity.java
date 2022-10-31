package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.intro.IntroActivity;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements FilmSearchAdapter.OnSearchItemClickListener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FilmSearchAdapter filmSearchAdapter;
    ArrayList<Film> listFilm;
    ImageView imgBackToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imgBackToHome = findViewById(R.id.imgSearchBackToHome);
        imgBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackToHome = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intentBackToHome);
            }
        });

        listFilm = new ArrayList<>();
        recyclerView = findViewById(R.id.searchRecyclerView);

        filmSearchAdapter = new FilmSearchAdapter(listFilm,this);
        recyclerView.setAdapter(filmSearchAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        Query query = databaseReference.child("films");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFilm.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Film film = dataSnapshot.getValue(Film.class);
                        listFilm.add(film);
                }
                filmSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void OnSearchItemClickListener(Film film) {
        Bundle bundle = new Bundle();

        bundle.putString("backdrop", film.getBackdrop());
        bundle.putString("name", film.getName());
        bundle.putString("country", film.getCountry());
        bundle.putString("limitedAge", String.valueOf(film.getLimitedAge()));
        bundle.putString("desc", film.getDesc());

        Intent intent = new Intent(SearchActivity.this, MovieDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}