package com.sinhvien.android_java_onmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.model.Film;

public class StreamFilmActivity extends AppCompatActivity {

    String videolink;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_film);

       videolink = "Video1.mp4";
       videoView = findViewById(R.id.videoView);

       StorageReference storageReference = FirebaseStorage.getInstance().getReference();

       StorageReference videoref = storageReference.child("sourcevideos/films/" + videolink);
       videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               videoView.setVideoURI(uri);
           }
       });

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();

    }
}