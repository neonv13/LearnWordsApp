package com.example.learnwordsapp;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.LiveData;

import com.example.learnwordsapp.Room.Question;
import com.example.learnwordsapp.Room.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {


    //Activity Components
    private EditText emailText;
    private EditText passwordText;
    private View signInButton;
    private View createAccountButton;
    private ImageView fingerprintImage;

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
        fingerprintImage = findViewById(R.id.fingerprintImage);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        executor = ContextCompat.getMainExecutor(this);

        boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
        if(isLogin){
            fingerprintImage.setVisibility(View.VISIBLE);
        }

//        biometricPrompt = new androidx.biometric.BiometricPrompt(LoginActivity.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback()
//        {
//            @Override
//            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                super.onAuthenticationError(errorCode, errString);
//                Toast.makeText(getApplicationContext(),"Authentication error: " + errString, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
//                super.onAuthenticationSucceeded(result);
//
//                String email = sharedPreferences.getString("email","");
//                String password = sharedPreferences.getString("password","");
//
//                Login(email,password);
//            }
//
//            @Override
//            public void onAuthenticationFailed() {
//                super.onAuthenticationFailed();
//                Toast.makeText(getApplicationContext(),"Authentication failed", Toast.LENGTH_SHORT).show();
//            }
//        });

        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Use fingerprint to log in")
                .setSubtitle("Log in using fingerprint")
                .setNegativeButtonText("Use password")
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if (TextUtils.isEmpty(email)){
                    emailText.setError("You need to enter your email");
                }
                if (TextUtils.isEmpty(password)){
                    passwordText.setError("You need to enter your password");
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

        fingerprintImage.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }

    private void Login(String email, String password){
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //updateUserCredentials(firebaseAuth.getCurrentUser(),"Karolina",email);
                    SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putBoolean("isLogin",true);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Logged Successfully", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(LoginActivity.this, UserProfile.class));
                    startActivity(new Intent(LoginActivity.this, QuizMainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login failed: "+task.getException(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void updateUserCredentials(@NonNull FirebaseUser fUser,@NonNull String displayName,@NonNull String eMail){
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
