package com.example.learnwordsapp;

import static com.example.learnwordsapp.LevelTestMainActivity.list;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.Collections;
import java.util.List;

public class LevelTestDashboardActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timerValue = 20;
    RoundedHorizontalProgressBar progressBar;
    List<Model> allQuestionslist;
    Model modelclass;
    int index = 0;

    TextView card_question, optiona, optionb, optionc, optiond;
    CardView cardOA, cardOC, cardOB, cardOD;

    int correctCount = 0;
    int wrongCount = 0;

    LinearLayout continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_test_dashboard);

        Hooks();

        allQuestionslist = list;
        Collections.shuffle(allQuestionslist);
        modelclass = list.get(index);

        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));

        continueBtn.setClickable(false);

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(LevelTestDashboardActivity.this);
                dialog.setContentView(R.layout.activity_time_out);

                dialog.findViewById(R.id.btn_tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LevelTestDashboardActivity.this, LevelTestMainActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        }.start();

        setAllData();
    }

    private void setAllData() {

        card_question.setText(modelclass.getQuestion());
        optiona.setText(modelclass.getoA());
        optionb.setText(modelclass.getoB());
        optionc.setText(modelclass.getoC());
        optiond.setText(modelclass.getoD());
        timerValue = 20;
        countDownTimer.cancel();
        countDownTimer.start();

    }

    private void Hooks() {

        progressBar = findViewById(R.id.quiz_timer);
        card_question = findViewById(R.id.card_question);
        optiona = findViewById(R.id.card_optiona);
        optionb = findViewById(R.id.card_optionb);
        optionc = findViewById(R.id.card_optionc);
        optiond = findViewById(R.id.card_optiond);

        cardOA = findViewById(R.id.cardA);
        cardOB = findViewById(R.id.cardB);
        cardOC = findViewById(R.id.cardC);
        cardOD = findViewById(R.id.cardD);

        continueBtn = findViewById(R.id.continueBtn);

    }

    public void Correct(CardView cardView) {

        cardView.setBackgroundColor(getResources().getColor(R.color.green));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount++;
                index++;
                modelclass = list.get(index);
                resetColor();
                setAllData();
                enableButton();
            }
        });
    }

    public void Wrong(CardView cardOA) {

        cardOA.setBackgroundColor(getResources().getColor(R.color.red));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if (index < list.size() - 1) {
                    index++;
                    modelclass = list.get(index);
                    resetColor();
                    setAllData();
                    enableButton();
                } else {
                    EndQuiz();
                }
            }
        });
    }

    private void EndQuiz() {
        Intent intent = new Intent(LevelTestDashboardActivity.this, LevelTestEndActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);

        startActivity(intent);
    }

    public void enableButton() {
        cardOA.setClickable(true);
        cardOB.setClickable(true);
        cardOC.setClickable(true);
        cardOD.setClickable(true);
    }

    public void disableButton() {
        cardOA.setClickable(false);
        cardOB.setClickable(false);
        cardOC.setClickable(false);
        cardOD.setClickable(false);
    }

    public void resetColor() {
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void OptionClickA(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if (modelclass.getoA().equals(modelclass.getAns())) {
            cardOA.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOA);
            } else {
                EndQuiz();
            }
        } else {
            Wrong(cardOA);
        }
    }

    public void OptionClickB(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if (modelclass.getoB().equals(modelclass.getAns())) {
            cardOB.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOB);
            } else {
                EndQuiz();
            }
        } else {
            Wrong(cardOB);
        }
    }

    public void OptionClickC(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if (modelclass.getoC().equals(modelclass.getAns())) {
            cardOC.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOC);
            } else {
                EndQuiz();
            }
        } else {
            Wrong(cardOC);
        }
    }

    public void OptionClickD(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if (modelclass.getoD().equals(modelclass.getAns())) {
            cardOD.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(cardOD);
            } else {
                EndQuiz();
            }
        } else {
            Wrong(cardOD);
        }
    }
}
