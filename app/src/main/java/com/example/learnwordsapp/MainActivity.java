package com.example.learnwordsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_activity);

        bottomNavigationView = findViewById(R.id.navigation2);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()== R.id.nav_ranking){
                    Fragment f = RankingFragment.newInstance();
                    FragmentTransaction r = getSupportFragmentManager().beginTransaction();
                    r.replace(R.id.frame_layout, f);
                    r.commit();
                    return true;
                }

                /*if(item.getItemId()== R.id.nav_user){
                    Fragment f = RankingFragment.newInstance();
                    FragmentTransaction r = getSupportFragmentManager().beginTransaction();
                    r.replace(R.id.frame_layout, f);
                    r.commit();
                    return true;
                }*/

                if(item.getItemId()== R.id.nav_home){
                    Fragment f = HomeFragment.newInstance();
                    FragmentTransaction r = getSupportFragmentManager().beginTransaction();
                    r.replace(R.id.frame_layout, f);
                    r.commit();
                    return true;
                }

                return true;
           }
        });

    }

}