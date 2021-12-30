package com.example.learnwordsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String questionPL;
    private String questionENG;

    public Question(String questionPL, String questionENG) {
        this.questionPL = questionPL;
        this.questionENG = questionENG;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getQuestionPL() {
        return questionPL;
    }

    public String getQuestionENG() {
        return questionENG;
    }
}
