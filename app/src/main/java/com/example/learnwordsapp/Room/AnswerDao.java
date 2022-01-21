package com.example.learnwordsapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learnwordsapp.Room.Answer;

import java.util.List;

@Dao
public interface AnswerDao {

    @Insert
    void insert(Answer answer);

    @Update
    void update(Answer answer);

    @Delete
    void delete(Answer answer);

    @Query("SELECT * FROM answer_table") // WHERE Category something ?
    LiveData<List<Answer>> getAllAnswer();

    @Query("SELECT * FROM answer_table WHERE id == :id") // WHERE Category something ?
    List<Answer> getAnswers(int id);


}
