package com.sinhvien.android_java_onmovie;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.model.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class FilterAdapter extends FragmentStateAdapter {

    ArrayList<Genre> genreList;


    public FilterAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList genreList) {
        super(fragmentManager, lifecycle);
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new FilterFragment();

        Genre item = genreList.get(position);

        Bundle args = new Bundle();
        args.putString("genre_id", item.getId());
        args.putString("genre_name", item.getName());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }
}
