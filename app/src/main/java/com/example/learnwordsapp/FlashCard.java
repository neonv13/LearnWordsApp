package com.example.learnwordsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashCard_table")
public class FlashCard {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String wordPL;
    private String wordEng;

    public FlashCard(String wordPL, String wordEng) {
        this.wordPL = wordPL;
        this.wordEng = wordEng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWordPL() {
        return wordPL;
    }

    public String getWordEng() {
        return wordEng;
    }
}
