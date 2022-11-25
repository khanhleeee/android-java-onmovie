package com.sinhvien.android_java_onmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolderCountryTag> {

    public interface OnCountryTagClickListener {
        void onCountryTagClickListener(Country country);
    }

    public class ViewHolderCountryTag extends RecyclerView.ViewHolder {
        TextView tag;
        public ViewHolderCountryTag(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.tag);
        }
    }

    private ArrayList<Country> countries;
    private OnCountryTagClickListener mListener;

    public CountryAdapter(ArrayList<Country> countries, OnCountryTagClickListener mListener) {
        this.countries = countries;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolderCountryTag onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_tag, parent, false);
        return new ViewHolderCountryTag(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCountryTag holder, int position) {
        Country country = countries.get(position);

        holder.tag.setText(country.getName());

        holder.itemView.setOnClickListener(view -> {
            mListener.onCountryTagClickListener(country);
        });


    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

}
