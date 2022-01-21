package com.example.learnwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class SentenceMainActivity extends AppCompatActivity {

    public static ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_main);

        list = new ArrayList<>();

        list.add(new Model("He loves the latest version of his operating system as it has" + "  ＿  " + "new software ", "much", "a few", "lots of", "many", "lots of"));
        list.add(new Model("'Artifcial intelligence will never be more powerful than humar intekkigence.' The speaker is  ＿.", "advising", "giving an opinion", "saying he has doubts", "describing", "giving an opinion"));
        list.add(new Model("Some people feel more comfortable doing the work in Word first an then copying and  ＿  into Excel afterwards.", "pasting", "fastening", "fixing", "sticking", "pasting"));
        list.add(new Model("  ＿  has the air conditioning stopped working? I don't understand it.", "What", "Which", "Why", "When", "Why"));
        list.add(new Model("I couldn't stop laughing at the robot cat - it was really  ＿.", "amusing", "brief", "casual", "miserable", "amusing"));
        list.add(new Model("  ＿  a problem with the electronic display boards at the airport, several passengers were late for their flights.", "Therefore", "Due to", "Despite", "Because", "Due to"));
        list.add(new Model("'My screen isn't working again - I'll have to replace it.' 'Borrow one of mine. I've got two.' The second speaker is  ＿.", "offering help", "giving instructions", "asking for information", "giving advice", "offering help"));
        list.add(new Model("Now  ＿  return and the data will be saved", "post", "point", "press", "push", "press"));
        list.add(new Model("In hot countries, people need  ＿  to keep buildings cool.", "engies", "temperatures", "heaters", "air conditioning", "air conditioning"));
        list.add(new Model("I'm tired of my smartphone always restarting. The speaker is  ＿.", "amused", "annoyed", "suprised", "excited", "annoyed"));
        list.add(new Model("I don't like this website  ＿  do you? I can't find anything I need.", "a lot of", "many", "very", "much", "much"));
        list.add(new Model("Exit the game by pressing  ＿  here.", "this button", "a button", "button", "that button", "this button"));
        list.add(new Model("Do I click on the basket to go to  ＿?", "cash machine", "cashpoint", "credit card", "checkout", "checkout"));
        list.add(new Model("Have you  ＿  of smart sunglasses?", "listened", "looked", "heard", "seen", "heard"));
        list.add(new Model("I'm trying to avoid  ＿  my mobile outside working hours.", "answering", "to answer", "answer", "answered", "answering"));
        list.add(new Model("The printer just isn't fast  ＿. We need to order a new one.", "already", "such", "enough", "even", "enough"));
        list.add(new Model("Could you send me an email and  ＿  your report, please", "attach", "join", "fasten", "connect", "attach"));
        list.add(new Model("I've made a request to my boss  ＿  a new keyboard.", "with", "for", "in", "by", "for"));
        list.add(new Model("I disagree  ＿  you about how useful robots are.", "against", "from", "with", "by", "with"));
        list.add(new Model("David's laptop stopped working  ＿  a video call with his family back home yesterday evening.", "since", "while", "within", "during", "during"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SentenceMainActivity.this, SentenceDashboardActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}