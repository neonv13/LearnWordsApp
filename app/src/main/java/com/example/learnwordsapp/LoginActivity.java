package com.example.learnwordsapp;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    //Activity Components
    private EditText emailText;
    private EditText passwordText;
    private View signInButton;
    private View createAccountButton;
    private ImageView fingerprintImage;
    private View resetButton;

    //Firebase
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private ProgressDialog progressDialog;

    //Biometric
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Components
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        signInButton = findViewById(R.id.signInButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        resetButton = findViewById(R.id.resetButton);
        fingerprintImage = findViewById(R.id.fingerprintImage);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        executor = ContextCompat.getMainExecutor(this);

        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            fingerprintImage.setVisibility(View.VISIBLE);
        }

        biometricPrompt = new androidx.biometric.BiometricPrompt(LoginActivity.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), getString(R.string.authentication_error) + ": " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                String email = sharedPreferences.getString("email", "");
                String password = sharedPreferences.getString("password", "");

                Login(email, password);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.use_fingerprint))
                .setSubtitle(getString(R.string.log_in_fingerprint))
                .setNegativeButtonText(getString(R.string.use_password))
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailText.setError(getString(R.string.error_need_enter_email));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordText.setError(getString(R.string.error_need_enter_password));
                    return;
                }
                Login(email, password);
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });

        fingerprintImage.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }

    private void Login(String email, String password) {
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //updateUserCredentials(firebaseAuth.getCurrentUser(),"Karolina",email);
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putBoolean("isLogin", true);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, getString(R.string.logged_successfully), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failed) + ": " + task.getException(), Toast.LENGTH_LONG).show();
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
