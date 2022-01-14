package com.example.learnwordsapp.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private AnswerDao ansewerDao;
    private LiveData<List<Answer>> allAnswer;

    private FlashCardDao flashCardDao;
    private LiveData<List<FlashCard>> allFlashCard;

    private FlashCardPersonalDao flashCardPersonalDao;
    private LiveData<List<FlashCardPersonal>>allFlashCardPersonal;

    private QuestionDao questionDao;
    private LiveData<List<Question>>allQuestion;

    public Repository(Application application){
        LearnDatabase learnDatabase = LearnDatabase.getInstance(application);
        //assign db dao to our variable in this calss
        // and fetch data from db to list

        ansewerDao = learnDatabase.answerDao();
        allAnswer = ansewerDao.getAllAnswer();

        flashCardDao = learnDatabase.flashCardDao();
        allFlashCard = flashCardDao.getAllFlashCard();

        flashCardPersonalDao = learnDatabase.flashCardPersonalDao();
        allFlashCardPersonal = flashCardPersonalDao.getAllFlashCard();

        questionDao = learnDatabase.questionDao();
        allQuestion = questionDao.getAllQuestion();


    }

    //Methods to do db operations
    public void insertAnswer(Answer answer){
        new InsertAnswerAsync(ansewerDao).execute(answer);
    }
    public void update(Answer answer){
        new UpdateAnswerAsync(ansewerDao).execute(answer);
    }
    public void delete(Answer answer){
        new DeleteAnswerAsync(ansewerDao).execute(answer);
    }
    public LiveData<List<Answer>> getAllAnswer(){
        return allAnswer;
    }

    //Androind needs a Async data operation so ...
    //                                             AsyncTask<Input, Progres update, Output>
    private static class InsertAnswerAsync extends AsyncTask<Answer, Void, Void>{
        private AnswerDao answerDao;

        private InsertAnswerAsync(AnswerDao answerDao){
            this.answerDao = answerDao;
        }

        @Override
        protected Void doInBackground(Answer... answers) {
            answerDao.insert(answers[0]);
            return null;
        }
    }

    private static class UpdateAnswerAsync extends AsyncTask<Answer, Void, Void>{
        private AnswerDao answerDao;

        private UpdateAnswerAsync(AnswerDao answerDao){
            this.answerDao = answerDao;
        }

        @Override
        protected Void doInBackground(Answer... answers) {
            answerDao.update(answers[0]);
            return null;
        }
    }

    private static class DeleteAnswerAsync extends AsyncTask<Answer, Void, Void>{
        private AnswerDao answerDao;

        private DeleteAnswerAsync(AnswerDao answerDao){
            this.answerDao = answerDao;
        }

        @Override
        protected Void doInBackground(Answer... answers) {
            answerDao.delete(answers[0]);
            return null;
        }
    }
}
