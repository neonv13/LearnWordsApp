package com.example.learnwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class FlashcardsEndActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    LinearLayout ExitBtn;
    int correct, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards_end);

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
                BackToMain();

            }
        });
    }
    private void BackToMain() {
        Intent intent = new Intent(FlashcardsEndActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

