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
        private FlashCardPersonalDao flashCardPersonalDao;
        private QuestionDao questionDao;

        private PopulateDbAsyncTack(LearnDatabase db){
            this.answerDao = db.answerDao();
            this.flashCardDao = db.flashCardDao();
            this.flashCardPersonalDao = db.flashCardPersonalDao();
            this.questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Adding Data here == API fetch data here ?

            Question tmpQ = new Question("-", "Computer programs contained permanently in a hardware device (such as a read-only memory)");
            Answer tmpAnswer = new Answer("Firmware", tmpQ.getId());
            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
            questionDao.insert(tmpQ);
            answerDao.insert(tmpAnswer);
            answerDao.insert((new Answer("Malware", tmpQ.getId())));
            answerDao.insert((new Answer("Software", tmpQ.getId())));
            answerDao.insert((new Answer("Spyware", tmpQ.getId())));

            tmpQ = new Question("-", "Software that collects information about how someone uses the internet, or personal information such as passwords, without the user knowing about it");
            tmpAnswer = new Answer("Spyware", tmpQ.getId());
            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
            questionDao.insert(tmpQ);
            answerDao.insert(tmpAnswer);
            answerDao.insert((new Answer("Adware", tmpQ.getId())));
            answerDao.insert((new Answer("Abandonware", tmpQ.getId())));
            answerDao.insert((new Answer("Trojan", tmpQ.getId())));

            tmpQ = new Question("-", "A computer program that prevents advertisements from being displayed on a screen, for example when you visit a website");
            tmpAnswer = new Answer("Ad-blocker", tmpQ.getId());
            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
            questionDao.insert(tmpQ);
            answerDao.insert(tmpAnswer);
            answerDao.insert((new Answer("Spyware", tmpQ.getId())));
            answerDao.insert((new Answer("Abandonware", tmpQ.getId())));
            answerDao.insert((new Answer("Blocker", tmpQ.getId())));






            return null;
        }
    }

}
