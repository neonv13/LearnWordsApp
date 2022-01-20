package com.example.learnwordsapp.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String questionPL;
    private String questionENG;
    private int correctAnswerId;

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

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }
}
