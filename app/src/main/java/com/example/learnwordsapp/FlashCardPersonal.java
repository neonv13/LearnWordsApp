package com.example.learnwordsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashCardPersonal_table")
public class FlashCardPersonal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String wordPL;
    private String wordEng;

    public FlashCardPersonal(String wordPL, String wordEng) {
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
