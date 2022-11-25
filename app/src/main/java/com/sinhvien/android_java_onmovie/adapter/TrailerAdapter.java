package com.sinhvien.android_java_onmovie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.R;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.trailerView);


        }
    }

    ArrayList<String> trailers;

    public TrailerAdapter(ArrayList<String> trailers) {
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        String link = trailers.get(position);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference videoref = storageReference.child("sourcevideos/films/" + link);
        videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                holder.videoView.setVideoURI(uri);
            }
        });

        MediaController mediaController = new MediaController(holder.itemView.getContext());
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.requestFocus();
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }


}
