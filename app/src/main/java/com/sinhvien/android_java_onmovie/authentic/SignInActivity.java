package com.sinhvien.android_java_onmovie.authentic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sinhvien.android_java_onmovie.MainActivity;
import com.sinhvien.android_java_onmovie.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    public EditText etEmail, etPass;
    public Button btnLogin;
    public TextView tvSignUp;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvSignUp = findViewById(R.id.tvSignUp);
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginToSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(loginToSignUp);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etEmail.getText().toString();
                String password = etPass.getText().toString();
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Không được để trống");
                    return;
                }
                if (etPass.getText().toString().isEmpty()) {
                    etPass.setError("Không được để trống");
                    return;
                }
                if (etPass.length() < 6) {
                    etPass.setError("Mật khẩu không được dưới 6 ký tự");
                    return;
                }
                else {
                    Toast.makeText(SignInActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword("ngoquoctu113113@gmail.com", "123456")
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent loginToMain = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(loginToMain);
                                        Toast.makeText(SignInActivity.this,
                                                "LOGIN SUCCESSFUL !!!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignInActivity.this,
                                                "LOGIN UNSUCCESSFUL!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}