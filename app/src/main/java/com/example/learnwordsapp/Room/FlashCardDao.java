package com.example.learnwordsapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learnwordsapp.Room.FlashCard;

import java.util.List;

@Dao
public interface FlashCardDao {

    @Insert
    void insert(FlashCard flashCard);

    @Update
    void update(FlashCard flashCard);

    @Delete
    void delete(FlashCard flashcard);

    @Query("SELECT * FROM flashCard_table") // WHERE Category something ?
    LiveData<List<FlashCard>> getAllFlashCard();
}
