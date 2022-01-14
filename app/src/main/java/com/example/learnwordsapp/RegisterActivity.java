package com.example.learnwordsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //Activity Components
    private EditText emailText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText password2Text;
    private View createAccountButton;
    private View signInButton;

    //Firebase
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Components
        emailText = findViewById(R.id.emailTextRegister);
        usernameText = findViewById(R.id.usernameTextRegister);
        passwordText = findViewById(R.id.passwordTextRegister);
        password2Text = findViewById(R.id.confirmPasswordTextRegister);
        createAccountButton = findViewById(R.id.createAccountButtonRegister);
        signInButton = findViewById(R.id.signInButtonRegister);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String password2 = password2Text.getText().toString();
                if (Validate(email, password, password2))
                    return;
                Register(email, password);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean Validate(String email, String password, String password2) {
        if (TextUtils.isEmpty(email) == true){
            emailText.setError("You need to enter your email");
            return true;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            emailText.setError("Email is incorrect");
            return true;
        }
        if (TextUtils.isEmpty(password) == true){
            passwordText.setError("You need to enter your password");
            return true;
        }
        if (password2.equals(password) == false){
            password2Text.setError("Passwords have to be the same");
            return true;
        }
        if (password.length() < 5){
            passwordText.setError("Password must be more than 5 characters");
            return true;
        }
        if (password.equals(password.toLowerCase()) == true){
            passwordText.setError("Password must have at least one lowercase and uppercase letter");
            return true;
        }
        if (containsNumber(password) == false){
            passwordText.setError("Password must have at least one number");
            return true;
        }
        return false;
    }

    private boolean containsNumber(String password){
        for (int a=0; a<10; a++){
            String s=""+a;
            if(password.contains(s) == true)
                return true;
        }
        return false;
    }

    private void Register(String email, String password){
        String username = usernameText.getText().toString().trim();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    User u = new User(username, email);
                    String fbu = mAuth.getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference("/Users").child(fbu).setValue(u)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, "User '" + username + "' registered successfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, UserProfile.class);
                                        //Intent intent = new Intent(SignUpActivity.this, LoginUser.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                       //Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    //dopisujemy usera do Rankingu
                    FirebaseDatabase.getInstance().getReference("/Ranking").child("najlepsi").child(username).setValue(0);

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
