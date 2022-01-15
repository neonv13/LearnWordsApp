package com.example.learnwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class LevelTestMainActivity extends AppCompatActivity {

    public static ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_test_main);

        list = new ArrayList<>();

        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));
        list.add(new Model("Test", "1", "2", "3", "4", "1"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent= new Intent(LevelTestMainActivity.this, LavelTestDashboardActivity.class);
                //startActivity(intent);
            }
        }, 1500);
    }
}