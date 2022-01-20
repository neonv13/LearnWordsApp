package com.example.learnwordsapp.Room.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.learnwordsapp.Room.Answer;
import com.example.learnwordsapp.Room.Question;

import java.util.List;

public class QuestionsAndAnswers {
    @Embedded
    private Question question;
    @Relation(
            parentColumn = "id",
            entityColumn = "idQ"
    )
    private List<Answer> answers;


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
