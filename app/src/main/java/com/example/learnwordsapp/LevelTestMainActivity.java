package com.example.learnwordsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.learnwordsapp.ModelView.QuizModelView;
import com.example.learnwordsapp.Room.Answer;
import com.example.learnwordsapp.Room.Question;
import com.example.learnwordsapp.Room.Repository;


import java.util.ArrayList;
import java.util.List;

public class LevelTestMainActivity extends AppCompatActivity {

    private static final String TAG = "tmp";
    //    public static ArrayList<Model> list;
    public Repository repo;

    public Question q;

    private QuizModelView quizModelView;
    public static List<Question> allQuestions;
    public static List<Answer> allAnswers;

    public int tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);


        quizModelView = new ViewModelProvider(this).get(QuizModelView.class);
        quizModelView.getQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                allQuestions = questions;
            }
        });
        quizModelView.getAnswers().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                allAnswers = answers;
            }
        });

//        list = new ArrayList<>();
//
//        list.add (new Model("Computer programs contained permanently in a hardware device (such as a read-only memory)","Malware","Firmware","Software","Spyware","Firmware"));
//        list.add (new Model("Software that collects information about how someone uses the internet, or personal information such as passwords, without the user knowing about it","Spyware","Adware","Abandonware","Trojan horse","Spyware"));
//        list.add (new Model("Software that is no longer produced or supported by the company that originally made it","Abandonware","Trojan horse","Embedded system","Ad-blocker","Abandonware"));
//        list.add (new Model("A computer program that prevents advertisements from being displayed on a screen, for example when you visit a website","Blocker","Ad-blocker","Abandonware","Spyware","Ad-blocker"));
//        list.add (new Model("A computer program that has been deliberately designed to destroy information, or allow someone to steal it","Trojan horse","Software","Adware","Spyware","Trojan horse"));
//        list.add (new Model("A computer system that is created by software on one computer to copy the operations performed by a separate computer","Virtual computer","Virtual window","Virtual code","Virtual machine","Virtual machine"));
//        list.add (new Model("The basic language used to give instructions to a computer, consisting only of numbers","Computer systems ","Machine code","Machine learning","Code","Machine code"));
//        list.add (new Model("A computer system that does a particular task inside a machine or larger electrical system","Machine code","Virtual machine","Systems development","Embedded system","Embedded system"));
//        list.add (new Model("A program that automatically removes unwanted data from a computer's memory","Tip","Dumpster","Memory","Garbage collector","Garbage collector"));
//        list.add (new Model("Software that makes it possible for a computer to recognize a digital image of someone's face","Machine software","Machine code","Facial recognition software","AI","Facial recognition software"));
//        list.add (new Model("The instructions that control what a computer does; computer programs","Machine learing","Machine code","Software","Hardware","Software"));
//        list.add (new Model("The physical and electronic parts of a computer, rather than the instructions it follows","Software","Hardware","Embedded system","Abandonware","Hardware"));
//        list.add (new Model("Computer software that is designed so that it can be used by a lot of different people or companies","Software","Hardware","Packaged hardware","Packaged software","Packaged software"));
//        list.add (new Model("The study of how to produce machines that have some of the qualities that the human mind has, such as the ability to understand language, recognize pictures, solve problems, and learn","Artificial intellect","Artificial intelligence","Artificial quickness","Artificial cleverness","Artificial intelligence"));
//        list.add (new Model("Internet protocol: the technical rules that control communication on the internet","IP","RP","TP","PP","IP"));
//        list.add (new Model("The service of keeping and managing websites on a server (= large central computer)","Web service","Web application","Web hosting","Web browser","Web hosting"));
//        list.add (new Model("A dot followed by three letters, such as .doc or .jpg, that forms the end of the name of a computer document","Dot","File extension","Enlargement","Amplification","File extension"));
//        list.add (new Model("The study of computers and how they can be us","Computer science","Informatic","IT","Science about society","Computer science"));
//        list.add (new Model("A small picture of an image or page on a computer screen","Icon","Cursor","Thumbnail","Mouse","Thumbnail"));
//        list.add (new Model("The practice of getting a large number of people to each give small amounts of money in order to provide the finance for a project, typically using the internet","Crowdfunding","Government funding","Samecrunding","Funding","Crowdfunding"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(LevelTestMainActivity.this, LevelTestDashboardActivity.class);

                startActivity(intent);
            }
        }, 2000);
    }
}