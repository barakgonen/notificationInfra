package com.example.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    private EditText emailTxt;
    private EditText passwordTex;
    private EditText userNameTxt;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        passwordTex = findViewById(R.id.passwordEditText);
        emailTxt = findViewById(R.id.mailEditText);
        userNameTxt = findViewById(R.id.userName);
        findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {
        final String email = emailTxt.getText().toString().trim();
        final String password = passwordTex.getText().toString().trim();
        final String userName = userNameTxt.getText().toString().trim();
        if (email.isEmpty()) {
            emailTxt.setError("Email is required!");
            emailTxt.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            passwordTex.setError("Password is required! and must be 6 chars or more");
            passwordTex.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String deviceToken = instanceIdResult.getToken();
                            Log.e("newToken", deviceToken);
                            TokenRegistrationHandler.registerToken(userName, deviceToken);
                            if (task.isSuccessful()) {
                                startProfiledActivity();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    userLogin(email, password);
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.
                                            makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        }
    }

    private void startProfiledActivity() {
        Intent intent = new Intent(this, ProfiledActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    startProfiledActivity();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.
                            makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}