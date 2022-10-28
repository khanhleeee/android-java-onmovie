package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {


    ImageView backdrop_img;
    TextView tv_content_limitedAge, tv_country, tv_name, tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdrop_img = findViewById(R.id.backdrop_img);
        tv_content_limitedAge = findViewById(R.id.tv_content_limitedAge);
        tv_country = findViewById(R.id.txtCountries);
        tv_name = findViewById(R.id.tv_movie_name);
        tv_content = findViewById(R.id.txtContent);

        Bundle bundle = getIntent().getExtras();
        String backdrop  = bundle.getString("backdrop");
        String name  = bundle.getString("name");
        String age  = bundle.getString("limitedAge");
        String country  = bundle.getString("country");
        String content = bundle.getString("desc");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference backdropFilm = storageReference.child("images/backdrops/"+ backdrop);

        backdropFilm.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(backdrop_img);
            }
        });

        tv_name.setText(name);
        tv_country.setText(country);
        tv_content_limitedAge.setText(age);
        tv_content.setText(content);
    }
}

