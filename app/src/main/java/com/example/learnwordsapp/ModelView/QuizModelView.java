package com.example.learnwordsapp.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.learnwordsapp.Room.Answer;
import com.example.learnwordsapp.Room.Question;
import com.example.learnwordsapp.Room.Repository;

import java.util.List;

public class QuizModelView extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Question>> questions;
    private LiveData<List<Answer>> answers;


    public QuizModelView(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        questions = repository.getAllQuestion();
        answers = repository.getAllAnswer();
    }

    public LiveData<List<Question>> getQuestions(){
        return questions;
    }
    public LiveData<List<Answer>> getAnswers(){
        return answers;
    }



}
