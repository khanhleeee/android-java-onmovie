package com.sinhvien.android_java_onmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.CountryAdapter;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.model.Country;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;

public class FilterFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener, CountryAdapter.OnCountryTagClickListener {

    TextView tvName;
    RecyclerView rvFilms, rvCountries;

    Button btnNewest, btnOldest;

    FilmAdapter adapter;
    CountryAdapter cAdapter;

    ArrayList<Film> films;
    ArrayList<Country> countries;

    FirebaseDatabase fDB;
    DatabaseReference mDB;

    public String GERNE_ID;
    public String COUNTRY_ID;

    boolean isNew = true;


    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* KHAI BAO VA KHOI TAO */
        btnNewest = view.findViewById(R.id.btnNewest);
        btnOldest = view.findViewById(R.id.btnOldest);

        btnOldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNew = false;
                if(GERNE_ID.equals("all")) {
                    loadAllFilms();
                } else {
                    loadFilterFilms();
                }
                btnOldest.setBackgroundDrawable(getResources().getDrawable(R.drawable.season_btn_bg));
                btnNewest.setBackgroundDrawable(getResources().getDrawable(R.drawable.season_btn_grey));
            }
        });

        btnNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNew = true;
                if(GERNE_ID.equals("all")) {
                    loadAllFilms();
                } else {
                    loadFilterFilms();
                }
                btnNewest.setBackgroundDrawable(getResources().getDrawable(R.drawable.season_btn_bg));
                btnOldest.setBackgroundDrawable(getResources().getDrawable(R.drawable.season_btn_grey));
            }
        });

        GERNE_ID = getArguments().getString("genre_id");
        COUNTRY_ID = "all";

        films = new ArrayList();
        countries = new ArrayList<>();
        countries.add(new Country("all", "Tất cả"));

        tvName = view.findViewById(R.id.tvName);
        rvFilms = view.findViewById(R.id.rvFilms);
        rvCountries = view.findViewById(R.id.rvCountries);

        fDB = FirebaseDatabase.getInstance();
        mDB = fDB.getReference();

        loadCountries();

        if(GERNE_ID == "all") {
            loadAllFilms();
        } else {
            loadFilterFilms();
        }

        adapter = new FilmAdapter(films,this,1);
        rvFilms.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvFilms.setLayoutManager(gridLayoutManager);


        cAdapter = new CountryAdapter(countries, this);
        rvCountries.setAdapter(cAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCountries.setLayoutManager(linearLayoutManager);

        tvName.setText(getArguments().getString("genre_name"));
    }

    private void loadCountries() {
        mDB.child("countries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemList : snapshot.getChildren()) {
                    Country country = itemList.getValue(Country.class);
                    countries.add(country);
                }
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    btnNewest.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//    });

    private void loadAllFilms() {
        mDB = fDB.getReference("films");
        Query queryDate = mDB.orderByChild("year");
        films.clear();
        queryDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemList : snapshot.getChildren()) {
                    Film item = itemList.getValue(Film.class);
                    Log.d("TAG", "new: " + isNew);
                    if(COUNTRY_ID.equals("all")) {
                        if(isNew) {
                            films.add(0, item);
                        }else
                        films.add(item);
                        adapter.notifyDataSetChanged();

                    }

                    else {
                        if(item.getCountry().equals(COUNTRY_ID)) {
                            if(isNew) {
                                films.add(0, item);
                            }else
                                films.add(item);}
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFilterFilms() {
        mDB = fDB.getReference("films");
        Query queryDate = mDB.orderByChild("year");
        films.clear();
        queryDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemList : snapshot.getChildren()) {
                    Film item = itemList.getValue(Film.class);

                    if(COUNTRY_ID.equals("all")) {
                        for(int i = 0; i < item.getFilm_genres().size(); i++) {
                            String genre = item.getFilm_genres().get(i);
                            if(genre.equals(GERNE_ID)) {
                                if(isNew) {
                                    films.add(0, item);
                                }else
                                films.add(item);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        if(item.getCountry().equals(COUNTRY_ID)) {
                            for(int i = 0; i < item.getFilm_genres().size(); i++) {
                                String genre = item.getFilm_genres().get(i);
                                if(genre.equals(GERNE_ID)) {
                                    if(isNew) {
                                        films.add(0, item);
                                    }else
                                    films.add(item);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void OnFilmItemCLickListener(Film film) {

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

        Intent intent = new Intent(getContext(), MovieDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCountryTagClickListener(Country country) {
        COUNTRY_ID = country.getId();

        if(GERNE_ID.equals("all")) {
            loadAllFilms();
        } else {
            loadFilterFilms();
        }

        if(COUNTRY_ID.equals("all")) {
            tvName.setText(getArguments().getString("genre_name"));
        } else {
            tvName.setText(getArguments().getString("genre_name") + " - " + country.getName());
        }
    }
}