package com.example.learnwordsapp.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTack(instance).execute();
        }
    };

    private static class PopulateDbAsyncTack extends AsyncTask<Void, Void,Void>{
        private AnswerDao answerDao;
        private FlashCardDao flashCardDao;

        private PopulateDbAsyncTack(LearnDatabase db){
            this.answerDao = db.answerDao();
            this.flashCardDao = db.flashCardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Adding Data here == API fetch data here ?


//            answerDao.insert((new Answer("Text sample here1")));
//            answerDao.insert((new Answer("Text sample here2")));
//            answerDao.insert((new Answer("Text sample here3")));
//            answerDao.insert((new Answer("Text sample here4")));
//            answerDao.insert((new Answer("Text sample here5")));

            return null;
        }
    }

}
