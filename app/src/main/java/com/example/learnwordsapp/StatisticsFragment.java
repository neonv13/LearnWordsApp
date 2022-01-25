package com.example.learnwordsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatisticsFragment extends Fragment {

    View root;
    TextView placetxt;
    TextView scoretxt;
    TextView leveltxt;
    String userDisplayName;
    String score;
    String level;
    List<Ranking> najlepsi;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_statistics, container, false);
        placetxt = root.findViewById(R.id.placeTxt);
        scoretxt = root.findViewById(R.id.scoreTxt);
        leveltxt = root.findViewById(R.id.levelTxt);

        userDisplayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        getUserScore(new Callback() {
            @Override
            public void onCallback(String value) {
            }
        });

        getUserPosition(new Callback() {
            @Override
            public void onCallback(String value) {
            }
        });
        getUserLevel(new Callback() {
            @Override
            public void onCallback(String value) {
                // leveltxt.setText(score);
            }
        });
        return root;

    }

    private void getUserScore(Callback callback) {

        FirebaseDatabase.getInstance()
                .getReference("/Ranking")
                .child("najlepsi")
                .child(userDisplayName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.hasChildren()) {
                            String user = snapshot.getKey();
                            Object d = snapshot.getValue();
                            score = d.toString();
                            int scoreV = Integer.parseInt(score);
                            SetUserScore(scoreV);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }

    private void SetUserScore(int score) {
        scoretxt.setText(Integer.toString(score));
    }

    public void getUserPosition(Callback callback) {
        FirebaseDatabase.getInstance()
                .getReference("/Ranking")
                .child("najlepsi")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        najlepsi = new ArrayList<>();
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String user = data.getKey();
                                Object d = data.getValue();
                                String score = d.toString();
                                int scoreVal = Integer.valueOf(score);
                                Ranking local = new Ranking(user, scoreVal);
                                najlepsi.add(local);
                            }
                        }
                        if (najlepsi.size() <= 0) {//jeśli nie ma danych na liście
                            Ranking local = new Ranking("<NO DATA>", 0);
                            najlepsi.add(local);
                        } else {//jeśli są dane
                            Collections.sort(najlepsi, new Comparator<Ranking>() {
                                @Override
                                public int compare(Ranking ranking, Ranking t1) {
                                    return t1.getScore() - ranking.getScore();
                                }
                            });
                            SetUserPosition(userDisplayName);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void SetUserPosition(String userName) {
        for (int i = 0; i < najlepsi.size(); i++) {
            Ranking x = najlepsi.get(i);
            if (x.getUserName().equals(userName)) {
                String poz = Integer.toString(i + 1);
                placetxt.setText(poz);
                return;
            }
        }
        placetxt.setText("UNKNOWN");
    }
    public void getUserLevel(Callback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String fbu = mAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("/Users")
                .child(fbu)
                .child("Level")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String user = snapshot.getKey();
                        Object d = snapshot.getValue();
                        level = d.toString();
                        SetUserLevel(level);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void SetUserLevel(String score) {
        leveltxt.setText(score);
    }
}