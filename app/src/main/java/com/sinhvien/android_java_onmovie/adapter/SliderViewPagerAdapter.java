package com.sinhvien.android_java_onmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.MovieDetail;
import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;
import java.util.List;

public class SliderViewPagerAdapter extends RecyclerView.Adapter<SliderViewPagerAdapter.ViewHolder> {

    private List<Film> films;
    private Context context;


    public SliderViewPagerAdapter(List<Film> films, Context context) {
        this.films = films;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slider, parent, false);
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Film film = films.get(position);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference sliderRef = storageReference.child("images/backdrops/" + film.getBackdrop());

            sliderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(holder.backdrop);
                }
            });
            holder.name.setText(film.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();

                    bundle.putString("id", film.getId());
                    bundle.putString("backdrop", film.getBackdrop());
                    bundle.putString("name", film.getName());
                    bundle.putString("country", film.getCountry());
                    bundle.putString("limitedAge", String.valueOf(film.getLimitedAge()));
                    bundle.putString("desc", film.getDesc());

                    ArrayList videos = new ArrayList(film.getVideos());
                    bundle.putStringArrayList("videos", videos);

                    ArrayList trailers = new ArrayList(film.getTrailers());
                    bundle.putStringArrayList("trailers", trailers);

                    ArrayList genres = new ArrayList(film.getFilm_genres());
                    bundle.putStringArrayList("genres", genres);

                    ArrayList film_casts = new ArrayList(film.getFilm_casts());
                    bundle.putStringArrayList("cast", film_casts);

                    Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backdrop, ic_play;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ic_play = itemView.findViewById(R.id.icPlaySlider);
            backdrop = itemView.findViewById(R.id.slider_backdrop);
            name = itemView.findViewById(R.id.slider_name);
        }
    }
}
