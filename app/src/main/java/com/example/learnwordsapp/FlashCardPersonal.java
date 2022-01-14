package com.example.learnwordsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashCardPersonal_table")
public class FlashCardPersonal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String wordPL;
    private String wordEng;
    private String defPL;
    private String defEng;

    public FlashCardPersonal(String wordPL, String wordEng,String defPL,String defEng) {
        this.wordPL = wordPL;
        this.wordEng = wordEng;
        this.defPL=defPL;
        this.defEng=defEng;
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

    public String getDefPL() {
        return defPL;
    }

    public void setDefPL(String defPL) {
        this.defPL = defPL;
    }

    public String getDefEng() {
        return defEng;
    }

    public void setDefEng(String defEng) {
        this.defEng = defEng;
    }
}
