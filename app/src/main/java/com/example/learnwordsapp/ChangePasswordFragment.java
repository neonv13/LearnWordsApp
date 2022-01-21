package com.example.learnwordsapp;

import static com.example.learnwordsapp.RegisterActivity.containsNumber;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class ChangePasswordFragment extends Fragment {
    View root;

    private EditText text_old_password;
    private EditText text_new_password;
    private EditText text_new_password2;
    private ProgressDialog progressDialog;
    View button_change;
    View button_go_back;

    private FirebaseUser firebaseUser;

    public ChangePasswordFragment() {
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_change_password, container, false);

        text_old_password = root.findViewById(R.id.old_password);
        text_new_password = root.findViewById(R.id.new_password);
        text_new_password2 = root.findViewById(R.id.confirm_new_password);
        button_change = root.findViewById(R.id.change_button);
        button_go_back = root.findViewById(R.id.button_go_back);
        progressDialog = new ProgressDialog(getContext());
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old_password = text_old_password.getText().toString();
                String new_password = text_new_password.getText().toString();
                String new_password2 = text_new_password2.getText().toString();

                if (Validate(old_password, new_password, new_password2))
                    return;

                ChangePassword(old_password, new_password);
            }
        });

        button_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = EditProfileFragment.newInstance();
                FragmentTransaction r = getActivity().getSupportFragmentManager().beginTransaction();
                r.replace(R.id.frame_layout, f);
                r.commit();
            }
        });


        return root;
    }

    private boolean Validate(String old_password, String password, String password2) {
        if (TextUtils.isEmpty(old_password)) {
            text_old_password.setError("You need to enter your old password");
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            text_new_password.setError("You need to enter your new password");
            return true;
        }
        if (!password2.equals(password)) {
            text_new_password2.setError("Passwords have to be the same");
            return true;
        }
        if (password.length() < 5) {
            text_new_password.setError("Password must be more than 5 characters");
            return true;
        }
        if (password.equals(password.toLowerCase())) {
            text_new_password.setError("Password must have at least one lowercase and uppercase letter");
            return true;
        }
        if (!containsNumber(password)) {
            text_new_password.setError("Password must have at least one number");
            return true;
        }
        return false;
    }

    private void ChangePassword(String old_password, String new_password) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(firebaseUser.getEmail()), old_password);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseUser.updatePassword(new_password);

                    Fragment f = EditProfileFragment.newInstance();
                    FragmentTransaction r = getActivity().getSupportFragmentManager().beginTransaction();
                    r.replace(R.id.frame_layout, f);
                    r.commit();
                } else {
                    text_old_password.setError("Re-Authentication Failed");
                }
                progressDialog.dismiss();
            }
        });
    }
}