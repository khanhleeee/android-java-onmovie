package com.sinhvien.android_java_onmovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.android_java_onmovie.R;

import java.util.ArrayList;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    private ArrayList seasons;

    public SeasonAdapter(ArrayList seasons) {
        this.seasons = seasons;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        Button btn_season;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_season = itemView.findViewById(R.id.btn_season);
        }
    }


    @NonNull
    @Override
    public SeasonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.MyViewHolder holder, int position) {
        holder.btn_season.setText("Phan" + String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }
}
