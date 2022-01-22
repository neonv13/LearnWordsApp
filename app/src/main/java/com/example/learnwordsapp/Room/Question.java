package com.example.learnwordsapp.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String questionPL;
    private String questionENG;
    private String ansver;
    private int correctAnswerId;

    public Question(String questionPL, String questionENG, String ansver) {
//        this.id = id;
        this.questionPL = questionPL;
        this.questionENG = questionENG;
        this.ansver = ansver;
    }

    public String getAnsver() {
        return ansver;
    }

    public void setAnsver(String ansver) {
        this.ansver = ansver;
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

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }
}
