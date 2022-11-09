package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.adapter.SeasonAdapter;
import com.sinhvien.android_java_onmovie.model.Country;
import com.sinhvien.android_java_onmovie.model.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {


    private SeasonAdapter seasonAdapter;
    private RecyclerView rv_season;

    DatabaseReference mDB;

    ArrayList seasons, f_genres, f_genres_name;

    ImageView backdrop_img;
    TextView tv_content_limitedAge, tv_country, tv_name, tv_content, tv_genres;

    String name, backdrop, age, country, content, names = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdrop_img = findViewById(R.id.backdrop_img);
        tv_content_limitedAge = findViewById(R.id.tv_content_limitedAge);
        tv_country = findViewById(R.id.tv_country);
        tv_name = findViewById(R.id.tv_movie_name);
        tv_content = findViewById(R.id.txtContent);
        tv_genres = findViewById(R.id.txtGen);

        rv_season = findViewById(R.id.rv_seasons);

        Bundle bundle = getIntent().getExtras();
        backdrop  = bundle.getString("backdrop");
        name  = bundle.getString("name");
        age  = bundle.getString("limitedAge");
        country  = bundle.getString("country");
        content = bundle.getString("desc");
        seasons = bundle.getStringArrayList("videos");
        f_genres = bundle.getStringArrayList("genres");

        f_genres_name = new ArrayList();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        seasonAdapter = new SeasonAdapter(seasons);
        rv_season.setAdapter(seasonAdapter);
        rv_season.setLayoutManager(layoutManager);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference backdropFilm = storageReference.child("images/backdrops/"+ backdrop);

        backdropFilm.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(backdrop_img);
            }
        });

        tv_name.setText(name);
        tv_content_limitedAge.setText(age);
        tv_content.setText(content);
        loadCountries();
        loadGenres();
    }

    private void loadCountries() {
        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("countries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items: snapshot.getChildren()) {
                    Country item = items.getValue(Country.class);
                    if(item.getId().equals(country))
                        tv_country.setText(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadGenres() {

        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("genres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items: snapshot.getChildren()) {
                    Genre item = items.getValue(Genre.class);
                    for(int i = 0; i < f_genres.size(); i++) {
                        String f_genre = (String) f_genres.get(i);
                        if(item.getId().equals(f_genre)) {
                            f_genres_name.add(item.getName());
                        }
                    }
                }
                Log.d("hihi", "genre: " + f_genres_name);
                for(int i = 0; i < f_genres_name.size(); i++) {
                    Log.d("hihi2", "genre: " + f_genres_name.get(i));
                    if(i < f_genres_name.size() - 1) {
                        names += f_genres_name.get(i) + ", ";
                    } else {
                        names += f_genres_name.get(i);
                    }
                }
                tv_genres.setText(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}

