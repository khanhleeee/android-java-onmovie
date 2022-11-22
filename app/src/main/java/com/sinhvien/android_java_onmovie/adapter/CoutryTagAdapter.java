package com.sinhvien.android_java_onmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.android_java_onmovie.R;
import com.sinhvien.android_java_onmovie.model.Cast;
import com.sinhvien.android_java_onmovie.model.Country;

import java.util.List;

public class CoutryTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnCountryItemClickListener {
        void OnCountryItemClickListener(Country country);
    }

    List<Country> listCountry;
    private OnCountryItemClickListener mListener;

    public class ViewHolderCountry extends RecyclerView.ViewHolder {
        AppCompatButton tagCountry;
        public ViewHolderCountry(@NonNull View itemView) {
            super(itemView);
            tagCountry=itemView.findViewById(R.id.btnTagCountry);
        }
    }

    public CoutryTagAdapter(List<Country> listCountry, OnCountryItemClickListener mListener) {
        this.listCountry = listCountry;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_tag_country, parent, false);

        return new ViewHolderCountry(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Country  country = listCountry.get(position);
        ViewHolderCountry viewHolderCountry= (ViewHolderCountry) holder;

        viewHolderCountry.tagCountry.setText(country.getName());
    }

    @Override
    public int getItemCount() {
        return (listCountry == null) ? 0 : listCountry.size();
    }
}
