package com.example.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    private EditText emailTxt;
    private EditText passwordTex;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        passwordTex = findViewById(R.id.passwordEditText);
        emailTxt = findViewById(R.id.mailEditText);
        findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    txt.setText(token);
                } else {
                    txt.setText(task.getException().toString());
                }
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUser() {
        final String email = emailTxt.getText().toString().trim();
        final String password = passwordTex.getText().toString().trim();
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
                public void onComplete(@NonNull Task<AuthResult> task) {
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