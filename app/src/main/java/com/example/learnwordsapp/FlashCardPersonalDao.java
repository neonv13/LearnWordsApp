package com.example.learnwordsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlashCardPersonalDao {

    @Insert
    void insert(FlashCardPersonal flashCardPersonal);

    @Update
    void update(FlashCardPersonal flashCardPersonal);

    @Delete
    void delete(FlashCardPersonal flashCardPersonal);

    @Query("SELECT * FROM flashCardPersonal_table") // WHERE Category something ?
    LiveData<List<FlashCard>> getAllFlashCard();


}
