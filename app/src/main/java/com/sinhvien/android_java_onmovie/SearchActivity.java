package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements FilmSearchAdapter.OnSearchItemClickListener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FilmSearchAdapter filmSearchAdapter;
    ArrayList<Film> listFilm;


    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listFilm = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
        toolbar.setTitle("Tìm kiếm");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.searchRecyclerView);


        filmSearchAdapter = new FilmSearchAdapter(listFilm,this);
        recyclerView.setAdapter(filmSearchAdapter);



//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

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

    }
}