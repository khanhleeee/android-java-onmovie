package com.sinhvien.android_java_onmovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.authentic.SignUpActivity;
import com.sinhvien.android_java_onmovie.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateUserDialogFragment extends DialogFragment {
    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;

    DatabaseReference mDB;

    public TextView textView, tvNickname;
    public EditText edtNickname;
    public Button btnSave, btnCancel;

    public User user;

    public UpdateUserDialogFragment(User user) {
        this.user = user;
    }

    public UpdateUserDialogFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update_user, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNickname = view.findViewById(R.id.edtNickname);
        textView =  view.findViewById(R.id.textView11);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvNickname = view.findViewById(R.id.tvNickName);

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mDB = fDatabase.getReference();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                onClickUpdateNickname();
                dismiss();
//                Fragment UserFragment = new UserFragment();
//                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
//                fn.replace(R.id.dialog_user, UserFragment).commit();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        
        ShowDialog();

//        AlertDialog optionDialog = new AlertDialog.Builder(getContext()).create();
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                
//            }
//        });



    }

    public  void ShowDialog(){
        mDB.child("users").child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                edtNickname.setText(user.getNickname());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickUpdateNickname(){
                String strNickName = edtNickname.getText().toString().trim();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(String.valueOf(strNickName))
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                        .build();

                if (isNameChanged()){
                    Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                }
            }

    private boolean isNameChanged() {
        mDB.child("users").child(fAuth.getCurrentUser().getUid()).child("nickname").setValue(edtNickname.getText().toString());
        return true;

    }

}
