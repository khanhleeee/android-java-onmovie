package com.sinhvien.android_java_onmovie;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.adapter.TrailerAdapter;

import java.util.ArrayList;

public class TrailerFragment extends Fragment {

    ArrayList trailers;
    VideoView trailerVideoView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TrailerFragment() {
    }


    public static TrailerFragment newInstance(String param1, String param2) {
        TrailerFragment fragment = new TrailerFragment();
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
        return inflater.inflate(R.layout.fragment_trailer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trailers = new ArrayList();
        trailerVideoView = view.findViewById(R.id.trailerVideoView);

        Bundle bundle = getActivity().getIntent().getExtras();

        trailers = bundle.getStringArrayList("trailers");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference videoref = storageReference.child("sourcevideos/films/" + trailers.get(0));
        videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                trailerVideoView.setVideoURI(uri);
            }
        });

        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(trailerVideoView);
        trailerVideoView.setMediaController(mediaController);
        trailerVideoView.requestFocus();
    }
}