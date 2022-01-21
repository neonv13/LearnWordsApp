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

public class QuizEndActivity extends AppCompatActivity {

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


        String userDisplayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();


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
                            int finalScore = Integer.valueOf(d.toString());
                            finalScore += correct;
                            FirebaseDatabase.getInstance().getReference("/Ranking")
                                    .child("najlepsi")
                                    .child(userDisplayName)
                                    .setValue(finalScore);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void BackToMain() {
        Intent intent = new Intent(QuizEndActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

