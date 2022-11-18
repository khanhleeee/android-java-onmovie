package com.sinhvien.android_java_onmovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.model.Cast;
import com.sinhvien.android_java_onmovie.model.Film;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendedMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendedMovieFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener{

    FirebaseDatabase fDB;
    DatabaseReference mDB;

    RecyclerView recyclerView;
    FilmAdapter filmAdapter;

    ArrayList<Film> films;
    ArrayList genre;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecommendedMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendedMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendedMovieFragment newInstance(String param1, String param2) {
        RecommendedMovieFragment fragment = new RecommendedMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommended_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fDB = FirebaseDatabase.getInstance();
        mDB = fDB.getReference();

        Bundle bundle = getActivity().getIntent().getExtras();
        genre = bundle.getStringArrayList("genres");

        recyclerView= view.findViewById(R.id.rvRecommendFilm);
        filmAdapter = new FilmAdapter(films, this, 1);
        recyclerView.setAdapter(filmAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        mDB.child("films").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
//                    ArrayList test = (ArrayList) item.child("film_genres").getValue();
                    Film film = item.getValue(Film.class);
                    for(int i=0; i< film.getFilm_genres().size(); i++){
                        String test = film.getFilm_genres().get(i);
                        if(test.equals(genre)){
                            films.add(film);

                        }
                    }
//                    Log.d("ABC", ": "+test);
                }
                filmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void OnFilmItemCLickListener(Film film) {

    }
}