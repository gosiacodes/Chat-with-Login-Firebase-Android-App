package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail;
    EditText editTextPassword;
    ProgressBar progressBarSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBarSignUp = findViewById(R.id.progressBarSignUp);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;}
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Valid email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError("Minimum 6 characters are required");
            editTextPassword.requestFocus();
            return;
        }

        progressBarSignUp.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarSignUp.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("createUser", "createUser:success");
                            Toast.makeText(getApplicationContext(), "User registered successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (SignUpActivity.this, ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("createUser", "createUser:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "User already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            //updateUI(null);
                        }
                    }
                });
    }
}
