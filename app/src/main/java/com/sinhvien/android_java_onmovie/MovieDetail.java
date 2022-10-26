package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MovieDetail extends AppCompatActivity {

//    FirebaseDatabase fDatabase;
//    DatabaseReference databaseReference;
//    ImageView backdrop_img;
//    TextView tv_content_limitedAge;
//    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

//        fDatabase = FirebaseDatabase.getInstance();
//        dRestaurant = fDatabase.getReference();
//        backdrop_img = findViewById(R.id.backdrop_img);
//        tv_content_limitedAge = findViewById(R.id.tv_content_limitedAge);
//        btn = findViewById(R.id.btn);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickGetMovieDetail();
//            }
//        });
    }
}

