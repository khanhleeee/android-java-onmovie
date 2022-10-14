package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NickNameActivity extends AppCompatActivity {

    TextView tvNickName;
    Button btnSignUpComplete;
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        tvNickName = findViewById(R.id.tvNickName);
        btnSignUpComplete = findViewById(R.id.btnSignUpComplete);

        btnSignUpComplete.setOnClickListener(view -> {
            Bundle extras = getIntent().getExtras();
            String email, password, nickname;

            if (extras != null){
                email = extras.getString("email");
                password = extras.getString("password");
                nickname = tvNickName.getText().toString();
            } else {
                email = password = nickname = "";
            }

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){

                                userID = fAuth.getCurrentUser().getUid();
                                DatabaseReference databaseReference = fDatabase.getReference();

                                Map<String,Object> user = new HashMap<>();
                                user.put("email",email);
                                user.put("password",password);
                                user.put("nickname",nickname);

                                databaseReference.child("users").child(userID).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(NickNameActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(NickNameActivity.this, SignInActivity.class);
                                                intent.putExtra("email", email);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(NickNameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NickNameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        });

    }


}