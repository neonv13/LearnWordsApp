package com.example.learnwordsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class LevelTestEndActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    LinearLayout ExitBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int correct, wrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultText = findViewById(R.id.resultText);

        circularProgressBar.setProgress(correct);
        resultText.setText(correct + "/20");
        ExitBtn = findViewById(R.id.exitBtn);

        ExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateScore();
                BackToMain();

            }
        });
    }

    private void UpdateScore() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String fbu = mAuth.getCurrentUser().getUid();

        FirebaseDatabase.getInstance()
                .getReference("/Users")
                .child(fbu)
                .child("Level")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.hasChildren()) {

                            if(correct>17){
                                FirebaseDatabase.getInstance().getReference("/Users")
                                        .child(fbu)
                                        .child("Level")
                                        .setValue("Advanced");
                            }
                            if(correct>12){
                                FirebaseDatabase.getInstance().getReference("/Users")
                                        .child(fbu)
                                        .child("Level")
                                        .setValue("Intermediate");
                            }
                            if(correct<7){
                                FirebaseDatabase.getInstance().getReference("/Users")
                                        .child(fbu)
                                        .child("Level")
                                        .setValue("Beginner");
                            }
                            if(correct<4){
                                FirebaseDatabase.getInstance().getReference("/Users")
                                        .child(fbu)
                                        .child("Level")
                                        .setValue("Novice");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void BackToMain() {
        Intent intent = new Intent(LevelTestEndActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

