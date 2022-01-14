package com.example.learnwordsapp.Room;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_table")
public class Answer {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;

    public Answer(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
