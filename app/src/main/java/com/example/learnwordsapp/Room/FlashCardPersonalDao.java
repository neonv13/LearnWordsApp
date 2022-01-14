package com.example.learnwordsapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learnwordsapp.Room.FlashCard;
import com.example.learnwordsapp.Room.FlashCardPersonal;

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
    LiveData<List<FlashCardPersonal>> getAllFlashCard();


}
