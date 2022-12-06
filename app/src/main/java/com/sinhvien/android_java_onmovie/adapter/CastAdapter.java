package com.sinhvien.android_java_onmovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.model.Cast;
import com.sinhvien.android_java_onmovie.model.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnCastItemClickListener {
        void OnCastItemClickListener(Cast cast);
    }

    List<Cast> listCast;
    private OnCastItemClickListener mListener;

    public List<Cast> getListCast() {
        return listCast;
    }

    public void setListCast(List<Cast> listCast) {
        this.listCast = listCast;
    }

    public class ViewHolderCast extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView avatar;

        public ViewHolderCast(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_cast);
            avatar = itemView.findViewById(R.id.avatar_cast);
        }
    }

    public CastAdapter(List<Cast> listCasts, OnCastItemClickListener listener) {
        this.listCast = listCasts;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_cast, parent, false);

        return new ViewHolderCast(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cast cast = listCast.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        ViewHolderCast viewHolderCast = (ViewHolderCast) holder;
        StorageReference avatarCast = storageReference.child("images/casts/"+ cast.getAvatar());
        avatarCast.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderCast.avatar);
            }
        });
        viewHolderCast.name.setText(cast.getName());
    }

    @Override
    public int getItemCount() {
        return (listCast == null) ? 0 : listCast.size();
//        return listCast.size();
    }
}
