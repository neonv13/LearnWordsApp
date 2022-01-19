package com.example.learnwordsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Fragment f = ReauthenticateFragment.newInstance();
        FragmentTransaction r = getSupportFragmentManager().beginTransaction();
        r.replace(R.id.frame_layout, f);
        r.commit();
    }
}
