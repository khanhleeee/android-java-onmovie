package com.sinhvien.android_java_onmovie.authentic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinhvien.android_java_onmovie.MainActivity;
import com.sinhvien.android_java_onmovie.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    public EditText etEmail, etPass;
    public Button btnLogin;
    public TextView tvSignUp;
    public CheckBox checkBox;
    public ImageView ivGoogle;

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    FirebaseDatabase fDatabase;

    private ProgressDialog progressDoalog;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        progressDoalog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        tvSignUp = findViewById(R.id.tvSignUp);
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.checkBox);

        etEmail.setText(sharedPreferences.getString("taikhoan", ""));
        etPass.setText(sharedPreferences.getString("matkhau", ""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked", false));
        ivGoogle = findViewById(R.id.icGgLogin);

        ivGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });

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
//                progressDoalog.show();
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

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    progressDoalog.dismiss();
                                    if (task.isSuccessful()) {
                                        if (checkBox.isChecked()){
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("taikhoan", username);
                                            editor.putString("matkhau", password);
                                            editor.putBoolean("checked", true);
                                            editor.commit();
                                        }else {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.remove("taikhoan");
                                            editor.remove("matkhau");
                                            editor.remove("checked");
                                            editor.commit();
                                        }
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent loginToMain = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(loginToMain);
                                        Toast.makeText(SignInActivity.this,
                                                "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignInActivity.this,
                                                "Đăng nhập thất bại !!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    // [END signin]

    private void updateUI(FirebaseUser user) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

        if(user1 != null){
            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else {

        }

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