package com.example.learnwordsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

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
        if (TextUtils.isEmpty(email)) {
            emailText.setError(getString(R.string.error_need_enter_email));
            return true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.error_incorrect_email));
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            passwordText.setError(getString(R.string.error_need_enter_password));
            return true;
        }
        if (!password2.equals(password)) {
            password2Text.setError(getString(R.string.error_the_same_passwords));
            return true;
        }
        if (password.length() < 5) {
            passwordText.setError(getString(R.string.error_more_char_password));
            return true;
        }
        if (password.equals(password.toLowerCase())) {
            passwordText.setError(getString(R.string.error_lowercase_uppercase_password));
            return true;
        }
        if (!containsNumber(password)) {
            passwordText.setError(getString(R.string.error_number_password));
            return true;
        }
        return false;
    }

    public static boolean containsNumber(String password) {
        for (int a = 0; a < 10; a++) {
            String s = "" + a;
            if (password.contains(s))
                return true;
        }
        return false;
    }

    private void Register(String email, String password) {
        String username = usernameText.getText().toString().trim();
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateUserCredentials(Objects.requireNonNull(mAuth.getCurrentUser()), username, email);
                    User u = new User(username, email);
                    String fbu = mAuth.getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference("/Users").child(fbu).setValue(u)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, "User '" + username + "' registered successfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        //Intent intent = new Intent(SignUpActivity.this, LoginUser.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    //dopisujemy usera do Rankingu
                    FirebaseDatabase.getInstance().getReference("/Ranking").child("najlepsi").child(username).setValue(0);

                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.registration_failed) + ": " + task.getException(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void updateUserCredentials(@NonNull FirebaseUser fUser, @NonNull String displayName, @NonNull String eMail) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        fUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("updateProfile", "User profile updated.");
                        }
                    }
                });

        fUser.updateEmail(eMail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.d("updateEmail", "User email address updated.");
                        }
                    }
                });
    }
}
