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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Film> listFilm;
//    private OnSearchItemClickListener mListener;

//    public interface OnSearchItemClickListener {
//        void OnSearchItemClickListener(Film film);
//    }

    public FilmAdapter( List<Film> listFilms) {
        listFilm = listFilms;
//        mListener = mListener;
    }

    public class ViewHolderFilm extends RecyclerView.ViewHolder {
        TextView name;
        ImageView backdrop, btnPlay;

        public ViewHolderFilm(@NonNull  View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.tvFilmName);
            backdrop = itemView.findViewById(R.id.ivBackDrop);
            btnPlay = itemView.findViewById(R.id.ivBtnPlay);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_search_film, parent, false);

        return new ViewHolderFilm(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = listFilm.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        ViewHolderFilm viewHolderFilm =(ViewHolderFilm) holder;
        StorageReference backdropFilm = storageReference.child("films/"+ film.getBackdrop());
        backdropFilm.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderFilm.backdrop);
            }
        });
        viewHolderFilm.name.setText(film.getName());
//        viewHolderFilm.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.OnSearchItemClickListener(film);
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        return (listFilm == null) ? 0 : listFilm.size();
        return listFilm.size();
    }



}
