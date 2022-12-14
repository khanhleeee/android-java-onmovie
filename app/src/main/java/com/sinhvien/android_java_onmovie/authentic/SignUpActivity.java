package com.sinhvien.android_java_onmovie.authentic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sinhvien.android_java_onmovie.R;

public class SignUpActivity extends AppCompatActivity {

    EditText etEmailSignUp, etPasswordSignUp, etComfirmPasswordSignUp;
    Button btnSignUp;
    TextView tvSignInBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        etComfirmPasswordSignUp = findViewById(R.id.etComfirmPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignInBack = findViewById(R.id.tvSignInBack);

        tvSignInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etEmailSignUp.getText().toString().isEmpty()) {
                    etEmailSignUp.setError("Không được để trống");
                    return;
                }
                if (etPasswordSignUp.getText().toString().isEmpty()) {
                    etPasswordSignUp.setError("Không được để trống");
                    return;
                }
                if (etComfirmPasswordSignUp.getText().toString().isEmpty()) {
                    etComfirmPasswordSignUp.setError("Không được để trống");
                    return;
                }
                if (etPasswordSignUp.length() < 6) {
                    etPasswordSignUp.setError("Mật khẩu không được dưới 6 ký tự");
                    return;
                }
                if (etComfirmPasswordSignUp.length() < 6) {
                    etComfirmPasswordSignUp.setError("Mật khẩu không được dưới 6 ký tự");
                    return;
                }
                if (!etPasswordSignUp.getText().toString().equals(etComfirmPasswordSignUp.getText().toString())){
                    etComfirmPasswordSignUp.setError("Mật khẩu không trùng khớp");
                    return;
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("email", etEmailSignUp.getText().toString());
                    bundle.putString("password", etPasswordSignUp.getText().toString());
                    Intent e = new Intent(SignUpActivity.this,NickNameActivity.class);
                    e.putExtras(bundle);
                    Log.d("BabyFour", "" + e.putExtras(bundle));
                    startActivity(e);
                }

            }
        });

    }
}