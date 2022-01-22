package com.example.learnwordsapp.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.learnwordsapp.Model;

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
            int id = 1;

           id = addQuestion("--","Computer programs contained permanently in a hardware device (such as a read-only memory)","Malware","Firmware","Software","Spyware","Firmware", id);
           id = addQuestion("--","Software that collects information about how someone uses the internet, or personal information such as passwords, without the user knowing about it","Spyware","Adware","Abandonware","Trojan horse","Spyware",id);
           id = addQuestion("--","Software that is no longer produced or supported by the company that originally made it","Abandonware","Trojan horse","Embedded system","Ad-blocker","Abandonware", id);
           id = addQuestion("--","A computer program that prevents advertisements from being displayed on a screen, for example when you visit a website","Blocker","Ad-blocker","Abandonware","Spyware","Ad-blocker",id);
           id = addQuestion("--","A computer program that has been deliberately designed to destroy information, or allow someone to steal it","Trojan horse","Software","Adware","Spyware","Trojan horse",id);
           id = addQuestion("--","A computer system that is created by software on one computer to copy the operations performed by a separate computer","Virtual computer","Virtual window","Virtual code","Virtual machine","Virtual machine",id);
           id = addQuestion("--","The basic language used to give instructions to a computer, consisting only of numbers","Computer systems ","Machine code","Machine learning","Code","Machine code",id);
           id = addQuestion("--","A computer system that does a particular task inside a machine or larger electrical system","Machine code","Virtual machine","Systems development","Embedded system","Embedded system",id);
           id = addQuestion("--","A program that automatically removes unwanted data from a computer's memory","Tip","Dumpster","Memory","Garbage collector","Garbage collector",id);
           id = addQuestion("--","Software that makes it possible for a computer to recognize a digital image of someone's face","Machine software","Machine code","Facial recognition software","AI","Facial recognition software",id);
           id = addQuestion("--","The instructions that control what a computer does; computer programs","Machine learing","Machine code","Software","Hardware","Software",id);
           id = addQuestion("--","The physical and electronic parts of a computer, rather than the instructions it follows","Software","Hardware","Embedded system","Abandonware","Hardware",id);
           id = addQuestion("--","Computer software that is designed so that it can be used by a lot of different people or companies","Software","Hardware","Packaged hardware","Packaged software","Packaged software",id);
           id = addQuestion("--","The study of how to produce machines that have some of the qualities that the human mind has, such as the ability to understand language, recognize pictures, solve problems, and learn","Artificial intellect","Artificial intelligence","Artificial quickness","Artificial cleverness","Artificial intelligence",id);
           id = addQuestion("--","Internet protocol: the technical rules that control communication on the internet","IP","RP","TP","PP","IP",id);
           id = addQuestion("--","The service of keeping and managing websites on a server (= large central computer)","Web service","Web application","Web hosting","Web browser","Web hosting",id);
           id = addQuestion("--","A dot followed by three letters, such as .doc or .jpg, that forms the end of the name of a computer document","Dot","File extension","Enlargement","Amplification","File extension",id);
           id = addQuestion("--","The study of computers and how they can be us","Computer science","Informatic","IT","Science about society","Computer science",id);
           id = addQuestion("--","A small picture of an image or page on a computer screen","Icon","Cursor","Thumbnail","Mouse","Thumbnail",id);
           id = addQuestion("--","The practice of getting a large number of people to each give small amounts of money in order to provide the finance for a project, typically using the internet","Crowdfunding","Government funding","Samecrunding","Funding","Crowdfunding",id);

//            Question tmpQ = new Question("-", "Computer programs contained permanently in a hardware device (such as a read-only memory)", ansver);
//            Answer tmpAnswer = new Answer("Firmware", tmpQ.getId());
//            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
//            questionDao.insert(tmpQ);
//            answerDao.insert(tmpAnswer);
//            answerDao.insert((new Answer("Malware", tmpQ.getId())));
//            answerDao.insert((new Answer("Software", tmpQ.getId())));
//            answerDao.insert((new Answer("Spyware", tmpQ.getId())));
//
//            tmpQ = new Question("-", "Software that collects information about how someone uses the internet, or personal information such as passwords, without the user knowing about it", ansver);
//            Answer tmpAnswer1 = new Answer("Spyware", tmpQ.getId());
//            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
//            questionDao.insert(tmpQ);
//            answerDao.insert(tmpAnswer);
//            answerDao.insert((new Answer("Adware", tmpQ.getId())));
//            answerDao.insert((new Answer("Abandonware", tmpQ.getId())));
//            answerDao.insert((new Answer("Trojan", tmpQ.getId())));
//
//            tmpQ = new Question("-", "A computer program that prevents advertisements from being displayed on a screen, for example when you visit a website", ansver);
//            tmpAnswer = new Answer("Ad-blocker", tmpQ.getId());
//            tmpQ.setCorrectAnswerId(tmpAnswer.getId());
//            questionDao.insert(tmpQ);
//            answerDao.insert(tmpAnswer);
//            answerDao.insert((new Answer("Spyware", tmpQ.getId())));
//            answerDao.insert((new Answer("Abandonware", tmpQ.getId())));
//            answerDao.insert((new Answer("Blocker", tmpQ.getId())));






            return null;
        }
        public int  addQuestion(String pl, String eng, String a1, String a2, String a3, String a4, String aCorrect, int id){
            questionDao.insert(new Question(pl, eng, aCorrect));
            answerDao.insert(new Answer(a1, id));
            answerDao.insert(new Answer(a2, id));
            answerDao.insert(new Answer(a3, id));
            answerDao.insert(new Answer(a4, id));

            return id + 1;
        }
    }



}
