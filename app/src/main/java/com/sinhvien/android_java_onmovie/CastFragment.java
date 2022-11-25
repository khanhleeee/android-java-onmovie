package com.sinhvien.android_java_onmovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.CastAdapter;
import com.sinhvien.android_java_onmovie.model.Cast;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastFragment extends Fragment implements CastAdapter.OnCastItemClickListener{

    FirebaseDatabase fDB;
    DatabaseReference mDB;

    RecyclerView recyclerViewCast;
    CastAdapter castAdapter;

    ArrayList<Cast> listCast;
    ArrayList<Cast> listCastFilm;
    ArrayList listCastNew;

    HashMap<String, Cast> test;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CastFragment newInstance(String param1, String param2) {
        CastFragment fragment = new CastFragment();
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
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fDB = FirebaseDatabase.getInstance();
        mDB = fDB.getReference();

        listCast = new ArrayList<>();
        Bundle bundle = getActivity().getIntent().getExtras();
        listCastNew = bundle.getStringArrayList("cast");

        test = new HashMap<>();

        recyclerViewCast= view.findViewById(R.id.rvListCasts);
        castAdapter = new CastAdapter(listCast,  this);
        recyclerViewCast.setAdapter(castAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewCast.setLayoutManager(gridLayoutManager);


        mDB.child("casts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item:snapshot.getChildren()){
                    test.put(item.getKey(),item.getValue(Cast.class));
                }
                    for(int i=0; i< listCastNew.size(); i++){
                       listCast.add(test.get(listCastNew.get(i)));
                    }

                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void OnCastItemClickListener(Cast cast) {

    }
}