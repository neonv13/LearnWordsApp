package com.example.learnwordsapp;

import static com.example.learnwordsapp.FlashcardsMainActivity.list;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Collections;
import java.util.List;

public class FlashcardsDashboardActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timerValue=20;
    List<ModelClass> allQuestionslist;
    ModelClass modelclass;
    int index = 0;

    TextView card_question,optiona,optionb,card_answear;
    CardView cardOA,cardOB;

    int correctCount = 0;
    int wrongCount = 0;

    LinearLayout continueBtn;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards_dashboard);

        Hooks();

        allQuestionslist = list;
        Collections.shuffle(allQuestionslist);
        modelclass=list.get(index);

        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));

        layout = findViewById(R.id.layout);

        continueBtn.setClickable(false);

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue = timerValue - 1;
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog (FlashcardsDashboardActivity.this);
                dialog.setContentView(R.layout.activity_time_out);

                dialog.findViewById(R.id.btn_tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FlashcardsDashboardActivity.this, MainActivity.class);
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
        card_answear.setText(modelclass.getAnswer());
        optiona.setText(modelclass.getoA());
        optionb.setText(modelclass.getoB());
        timerValue = 20;
        countDownTimer.cancel();
        countDownTimer.start();

    }

    private void Hooks() {

        card_question = findViewById(R.id.card_question);
        card_answear = findViewById(R.id.card_answer);
        optiona = findViewById(R.id.card_optiona);
        optionb = findViewById(R.id.card_optionb);

        cardOA = findViewById(R.id.cardA);
        cardOB = findViewById(R.id.cardB);

        continueBtn = findViewById(R.id.continueBtn);

    }

    public void Correct(CardView cardView){

        cardView.setBackgroundColor(getResources().getColor(R.color.green));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount++;
                index++;
                modelclass=list.get(index);
                resetColor();
                setAllData();
                enableButton();
            }
        });
    }

    public void Wrong(CardView cardOA){

        cardOA.setBackgroundColor(getResources().getColor(R.color.red));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if(index<list.size()-1){
                    index++;
                    modelclass = list.get(index);
                    resetColor();
                    setAllData();
                    enableButton();
                }else {
                    EndQuiz();
                }
            }
        });
    }

    private void EndQuiz(){
        Intent intent = new Intent(FlashcardsDashboardActivity.this, FlashcardsEndActivity.class);
        intent.putExtra("correct",correctCount);
        intent.putExtra("wrong",wrongCount);
        startActivity(intent);
    }

    public void enableButton(){
        cardOA.setClickable(true);
        cardOB.setClickable(true);
    }

    public void disableButton(){
        cardOA.setClickable(false);
        cardOB.setClickable(false);
    }

    public void resetColor(){
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void OptionClickA(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if(modelclass.getoA().equals(modelclass.getAns())){
            cardOA.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<list.size()-1){
                Correct(cardOA);
            }else {
                EndQuiz();
            }
        } else {
            Wrong(cardOA);
        }
    }

    public void OptionClickB(View view) {

        disableButton();
        continueBtn.setClickable(true);

        if(modelclass.getoB().equals(modelclass.getAns())){
            cardOB.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<list.size()-1){
                Correct(cardOB);
            }else {
                EndQuiz();
            }
        } else {
            Wrong(cardOB);
        }
    }

    public void expand(View view) {
        int v =(card_answear.getVisibility()== View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());

        card_answear.setVisibility(v);
    }
}