package com.sinhvien.android_java_onmovie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

    GoogleSignInOptions ggSignInOptions;
    GoogleSignInClient ggSignInClient;

    public TextView tvNickName, tvEmail, textView, noFilm;
    public ImageView imgAvatar, imgEdit;
    public EditText edtNickname;
    public ImageView imgSetting;

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

//        UserFragment fragmentA = new UserFragment();
//        getFragmentManager().beginTransaction()
//                .replace(R.id.user_fragment,fragmentA,"YOUR_TARGET_FRAGMENT_TAG")
//                .addToBackStack("YOUR_SOURCE_FRAGMENT_TAG").commit();

        tvNickName = view.findViewById(R.id.tvNickName);
        tvEmail = view.findViewById(R.id.tvEmail);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgEdit = view.findViewById(R.id.imgEdit);
        edtNickname = view.findViewById(R.id.edtNickname);
        textView =  view.findViewById(R.id.textView11);
        imgSetting = view.findViewById(R.id.imgSetting);
        tvEmpty = view.findViewById(R.id.tvEmpty);


        rvWatchList = view.findViewById(R.id.rvWatchList);

        films = new ArrayList();

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mDB = fDatabase.getReference();
        if(films.equals(null)){

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
//        }


//        loadWatchList();
        UpdateUser();
        ShowDialogSetting();
    }

    private void loadWatchList() {
        this.fDatabase.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("watch_lists").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
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
                                adapter.notifyDataSetChanged();
                                rvWatchList.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.INVISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
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

    public void ShowDialogSetting(){

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingUserDialogFragment dialog = new SettingUserDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "dialog_setting_user");
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
}


