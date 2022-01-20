package com.example.learnwordsapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.learnwordsapp.Room.Question;
import com.example.learnwordsapp.Room.relations.QuestionsAndAnswers;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insert(Question question);

    @Update
    void update(Question question);

    @Delete
    void delete(Question question);

    @Query("SELECT * FROM question_table") // WHERE Category something ?
    LiveData<List<Question>> getAllQuestion();


    @Transaction
    @Query("SELECT * FROM question_table WHERE id = :id")
    LiveData<List<QuestionsAndAnswers>> getQuestion(int id);
}
