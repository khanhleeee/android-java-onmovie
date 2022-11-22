package com.sinhvien.android_java_onmovie;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnRecommendItemClickListener{
        void OnRecommendItemClickListener(Film film);
    }


    public class ViewHolderFilmCardRec extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolderFilmCardRec(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.film_Img_Rec);
            name = itemView.findViewById(R.id.film_Name_Rec);
        }
    }

    private List<Film> films;
    private OnRecommendItemClickListener mListener;
    private int TYPE_LAYOUT;

    public RecommendAdapter(List<Film> films, OnRecommendItemClickListener mListener, int TYPE_LAYOUT) {
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
            View view = layoutInflater.inflate(R.layout.row_film_cart_recommend, parent, false);
            return new ViewHolderFilmCardRec(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = films.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();


        if(TYPE_LAYOUT == 1) {
            ViewHolderFilmCardRec viewHolder = (ViewHolderFilmCardRec) holder;
            StorageReference sliderRef = storageReference.child("images/posters/" + film.getBackdrop());

            sliderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolder.image);
                }
            });
            viewHolder.name.setText(film.getName());

            viewHolder.itemView.setOnClickListener(view -> {
                mListener.OnRecommendItemClickListener(film);
            });
        }
    }

    @Override
    public int getItemCount() {
        return films== null ? 0 :films.size();
    }
}
