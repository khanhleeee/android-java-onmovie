package com.sinhvien.android_java_onmovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnFilmItemCLickListener {
        void OnFilmItemCLickListener(Film film);
    }

    public class ViewHolderFilmCard extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolderFilmCard(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.film_Img);
            name = itemView.findViewById(R.id.film_Name);
        }
    }

    public class ViewHolderFilmCardDelete extends RecyclerView.ViewHolder{
        ImageView image, icDelete;
        TextView name;

        public ViewHolderFilmCardDelete(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.film_ImgDelete);
            name = itemView.findViewById(R.id.film_NameDelete);
            icDelete= itemView.findViewById(R.id.icDelete);
        }
    }

    private List<Film> films;
    private OnFilmItemCLickListener mListener;
    private int TYPE_LAYOUT;

    public FilmAdapter(List<Film> films, OnFilmItemCLickListener mListener, int TYPE_LAYOUT) {
        this.films = films;
        this.mListener = mListener;
        this.TYPE_LAYOUT = TYPE_LAYOUT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if(TYPE_LAYOUT == 1) {
            View view = layoutInflater.inflate(R.layout.row_film_card, parent, false);
            return new ViewHolderFilmCard(view);
        }
        else{
            View view = layoutInflater.inflate(R.layout.row_film_card_delete, parent, false);
            return new ViewHolderFilmCardDelete(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = films.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        if(TYPE_LAYOUT == 1) {
            ViewHolderFilmCard viewHolder = (ViewHolderFilmCard) holder;
            StorageReference sliderRef = storageReference.child("images/posters/" + film.getBackdrop());

            sliderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolder.image);
                }
            });
            viewHolder.name.setText(film.getName());

            viewHolder.itemView.setOnClickListener(view -> {
                mListener.OnFilmItemCLickListener(film);
            });
        }
        else  {
            ViewHolderFilmCardDelete viewHolder = (ViewHolderFilmCardDelete) holder;
            StorageReference sliderRef = storageReference.child("images/posters/" + film.getBackdrop());

            sliderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolder.image);
                }
            });
            viewHolder.name.setText(film.getName());
            viewHolder.icDelete.setVisibility(View.VISIBLE);
            viewHolder.itemView.setOnClickListener(view -> {
                mListener.OnFilmItemCLickListener(film);
            });
        }
    }

    @Override
    public int getItemCount() {
        return films== null ? 0 :films.size();
    }


}
