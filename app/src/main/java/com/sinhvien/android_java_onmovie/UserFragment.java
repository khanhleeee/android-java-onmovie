package com.sinhvien.android_java_onmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.adapter.FilmAdapter;
import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.model.Film;
import com.sinhvien.android_java_onmovie.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements FilmAdapter.OnFilmItemCLickListener{

    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;

    public TextView tvNickName, tvEmail, textView, noFilm;
    public ImageView imgAvatar, imgEdit;
    public EditText edtNickname;

    public RecyclerView rvWatchList;

    FilmAdapter adapter;
    ArrayList<Film> films;
    ArrayList filmlists;


    public  User user;

    DatabaseReference mDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvNickName = view.findViewById(R.id.tvNickName);
        tvEmail = view.findViewById(R.id.tvEmail);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgEdit = view.findViewById(R.id.imgEdit);
        edtNickname = view.findViewById(R.id.edtNickname);
        textView =  view.findViewById(R.id.textView11);
        noFilm = view.findViewById(R.id.tvNoFilm);



//        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//        dialog.setTitle("Xoá phim");
//        dialog.setMessage("Bạn muốn xoá phim khỏi danh sách yêu thích ?");
//        dialog.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getContext(),
//                        "Xoa thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        dialog.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getContext(),
//                        "Huy", Toast.LENGTH_SHORT).show();
//            }
//        });

        rvWatchList = view.findViewById(R.id.rvWatchList);

        films = new ArrayList();

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mDB = fDatabase.getReference();

        adapter = new FilmAdapter(films, this, 2);

        rvWatchList.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvWatchList.setLayoutManager(gridLayoutManager);


        this.fDatabase.getReference().child("users").child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                tvNickName.setText(user.getNickname());
                tvEmail.setText(user.getEmail());
            }

            public void onCancelled(DatabaseError error) {
            }
        });

        loadWatchList();
//        loadAllFilms();
        UpdateUser();
    }

    private void loadWatchList() {
        this.fDatabase.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("watch_lists").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemList : snapshot.getChildren()) {
                    filmlists = (ArrayList) snapshot.getValue();

                }
                mDB.child("films").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemList : snapshot.getChildren()) {
                            Film item = itemList.getValue(Film.class);
                            for (int i = 0; i < filmlists.size(); i++) {
                                if (item.getId().equals(filmlists.get(i))) {
                                    films.add(item);
                                }
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void UpdateUser(){

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserDialogFragment dialog = new UpdateUserDialogFragment(user);
                dialog.show(getActivity().getSupportFragmentManager(), "dialog_update_user");
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

        ArrayList genres = new ArrayList(film.getFilm_genres());
        bundle.putStringArrayList("genres", genres);

        ArrayList film_casts = new ArrayList(film.getFilm_casts());
        bundle.putStringArrayList("cast", film_casts);

        Intent intent = new Intent(getContext(), MovieDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}


//    private void loadAllFilms() {
//        mDB.child("films").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot itemList : snapshot.getChildren()) {
//                    Film item = itemList.getValue(Film.class);
//                    for(int i = 0; i < filmlists.size(); i++){
//                        if(item.getId().equals(filmlists.get(i))){
//                            films.add(item);
//                        }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

