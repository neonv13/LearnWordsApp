package com.example.learnwordsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class ReauthenticateFragment extends Fragment {
    View root;

    private EditText text_email;
    private EditText text_password;
    private ProgressDialog progressDialog;
    View button_cont;
    View button_go_back;

    private FirebaseUser firebaseUser;

    public ReauthenticateFragment() {
    }

    public static ReauthenticateFragment newInstance() {
        return new ReauthenticateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_reauthenticate, container, false);

        text_email = root.findViewById(R.id.emailText);
        text_password = root.findViewById(R.id.passwordText);
        button_cont = root.findViewById(R.id.button_continue);
        button_go_back = root.findViewById(R.id.button_go_back);
        progressDialog = new ProgressDialog(getContext());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        button_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = text_email.getText().toString();
                String password = text_password.getText().toString();

                if (Validate(email, password))
                    return;

                reauthenticateUser(email, password);
            }
        });

        button_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    assert activity != null;
                    activity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void reauthenticateUser(String email, String password) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Fragment f = EditProfileFragment.newInstance();
                    FragmentTransaction r = getActivity().getSupportFragmentManager().beginTransaction();
                    r.replace(R.id.frame_layout, f);
                    r.commit();
                } else {
                    Toast.makeText(getContext(), "Re-Authentication Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean Validate(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            text_email.setError("You need to enter your email");
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            text_password.setError("You need to enter your password");
            return true;
        }
        return false;
    }
}