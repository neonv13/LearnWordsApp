package com.example.learnwordsapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        FlashCard.class,
        Question.class,
        Answer.class,
        FlashCardPersonal.class
}, version = 1)

public abstract class LearnDatabase extends RoomDatabase {

    private static LearnDatabase instance;

    public abstract FlashCardDao flashCardDao();
    public abstract AnswerDao answerDao();
    public abstract QuestionDao questionDao();
    public abstract FlashCardPersonalDao flashCardPersonalDao();



    public static synchronized LearnDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LearnDatabase.class, "learn_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
