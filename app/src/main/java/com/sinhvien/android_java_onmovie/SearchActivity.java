package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;
import java.util.Locale;


public class SearchActivity extends AppCompatActivity implements FilmSearchAdapter.OnSearchItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Film> listFilm;
    FilmSearchAdapter filmSearchAdapter;

    ArrayList<Film> listFilmSearch;

    ImageView imgBackToHome, imgIconSearch;
    EditText inputSearch;
    TextView tvNoFilmSearch;
    String valueSearch;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputSearch = findViewById(R.id.etSearch);
        imgIconSearch = findViewById(R.id.imgIconSearch);
        tvNoFilmSearch = findViewById(R.id.tvNoFilm);

        imgBackToHome = findViewById(R.id.imgSearchBackToHome);
        imgBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackToHome = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intentBackToHome);
            }
        });

        listFilm = new ArrayList<>();
        listFilmSearch = new ArrayList<>();

//        list default
        recyclerView = findViewById(R.id.searchRecyclerView);
        filmSearchAdapter = new FilmSearchAdapter(listFilm, this);
        recyclerView.setAdapter(filmSearchAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        loadDefaultFilm();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        imgIconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueSearch = inputSearch.getText().toString();
//                int sizeInput = valueSearch.length();
//                Toast.makeText(SearchActivity.this, sizeInput+"", Toast.LENGTH_SHORT).show();
                databaseReference.child("films").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            listFilmSearch.clear();
                            for (int i = 0; i < listFilm.size(); i++) {
                                String nameFilm = listFilm.get(i).getName();

                                String lowerName = nameFilm.toLowerCase(Locale.ROOT);
                                String lowerInput = valueSearch.toLowerCase(Locale.ROOT);

                                if(lowerName.startsWith(lowerInput)){
                                    listFilmSearch.add(listFilm.get(i));
                                }
                            }
                                if(listFilmSearch.isEmpty() ){
                                    tvNoFilmSearch.setText("Từ khoá"+ "\" " + valueSearch + "\" " + "không tồn tại !" );
                                    tvNoFilmSearch.setVisibility(View.VISIBLE);
                                }
                                filmSearchAdapter.setListFilm(listFilmSearch);
                                filmSearchAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
            }
        });
        //Dung searchview de tim kiem phim
//        recyclerView = findViewById(R.id.searchRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        FirebaseRecyclerOptions<Film> options =
//                new FirebaseRecyclerOptions.Builder<Film>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference()
//                                .child("films"), Film.class)
//                        .build();
//        filmSearchAdapter = new FilmSearchAdapter(options);
//        recyclerView.setAdapter(filmSearchAdapter);
    }

    private void loadDefaultFilm (){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("films").addValueEventListener(new ValueEventListener() {
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        filmSearchAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        filmSearchAdapter.stopListening();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_search, menu);
//        MenuItem menuItem = menu.findItem(  R.id.app_bar_search);
//
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchData(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void searchData(String query) {
//        FirebaseRecyclerOptions<Film> options =
//                new FirebaseRecyclerOptions.Builder<Film>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference()
//                                .child("films").orderByChild("name").startAt(query).endAt(query+"\ufaff"), Film.class)
//                        .build();
//        filmSearchAdapter = new FilmSearchAdapter(options);
//        recyclerView.setAdapter(filmSearchAdapter);
//    }

//    @Override
//    public void OnSearchItemClickListener(Film film) {
//        Bundle bundle = new Bundle();
//
//        bundle.putString("backdrop", film.getBackdrop());
//        bundle.putString("name", film.getName());
//        bundle.putString("country", film.getCountry());
//        bundle.putString("limitedAge", String.valueOf(film.getLimitedAge()));
//        bundle.putString("desc", film.getDesc());
//
//        Intent intent = new Intent(SearchActivity.this, MovieDetail.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

}