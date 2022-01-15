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

    // Answers
    //Methods to do db operations
    public void insertAnswer(Answer answer){
        new InsertAnswerAsync(ansewerDao).execute(answer);
    }
    public void updateAnswer(Answer answer){
        new UpdateAnswerAsync(ansewerDao).execute(answer);
    }
    public void deleteAnswer(Answer answer){
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


    // FlashCard
    //Methods to do db operations
    public void insertFlashCard(FlashCard flashCard){
        new InsertFlashCardAsync(flashCardDao).execute(flashCard);
    }
    public void updateFlashCard(FlashCard flashCard){
        new UpdateFlashCardAsync(flashCardDao).execute(flashCard);
    }
    public void deleteFlashCard(FlashCard flashCard){
        new DeleteFlashCardAsync(flashCardDao).execute(flashCard);
    }
    public LiveData<List<FlashCard>> getAllFlashCard(){
        return allFlashCard;
    }

    //Androind needs a Async data operation so ...
    //
    private static class InsertFlashCardAsync extends AsyncTask<FlashCard, Void, Void>{
        private FlashCardDao flashCardDao;

        private InsertFlashCardAsync(FlashCardDao flashCardDao){
            this.flashCardDao = flashCardDao;
        }

        @Override
        protected Void doInBackground(FlashCard... flashCards) {
            flashCardDao.insert(flashCards[0]);
            return null;
        }
    }
    private static class UpdateFlashCardAsync extends AsyncTask<FlashCard, Void, Void>{
        private FlashCardDao flashCardDao;

        private UpdateFlashCardAsync(FlashCardDao flashCardDao){
            this.flashCardDao = flashCardDao;
        }

        @Override
        protected Void doInBackground(FlashCard... flashCards) {
            flashCardDao.update(flashCards[0]);
            return null;
        }
    }
    private static class DeleteFlashCardAsync extends AsyncTask<FlashCard, Void, Void>{
        private FlashCardDao flashCardDao;

        private DeleteFlashCardAsync(FlashCardDao flashCardDao){
            this.flashCardDao = flashCardDao;
        }

        @Override
        protected Void doInBackground(FlashCard... flashCards) {
            flashCardDao.delete(flashCards[0]);
            return null;
        }
    }


    // FlashCardPersonal
    //Methods to do db operations
    public void insertFlashCardPersonal(FlashCardPersonal flashCardPersonal){
        new InsertFlashCardPersonalAsync(flashCardPersonalDao).execute(flashCardPersonal);
    }
    public void updateFlashCardPersonal(FlashCardPersonal flashCardPersonal){
        new UpdateFlashCardPersonalAsync(flashCardPersonalDao).execute(flashCardPersonal);
    }
    public void deleteFlashCardPersonal(FlashCardPersonal flashCardPersonal){
        new DeleteFlashCardPersonalAsync(flashCardPersonalDao).execute(flashCardPersonal);
    }
    public LiveData<List<FlashCardPersonal>> getAllFlashCardPersonal(){
        return allFlashCardPersonal;
    }

    //Androind needs a Async data operation so ...
    //
    private static class InsertFlashCardPersonalAsync extends AsyncTask<FlashCardPersonal, Void, Void>{
        private FlashCardPersonalDao flashCardPersonalDao;

        private InsertFlashCardPersonalAsync(FlashCardPersonalDao flashCardPersonalDao ){
            this.flashCardPersonalDao = flashCardPersonalDao;
        }

        @Override
        protected Void doInBackground(FlashCardPersonal... flashCardPersonals) {
            flashCardPersonalDao.insert(flashCardPersonals[0]);
            return null;
        }
    }
    private static class UpdateFlashCardPersonalAsync extends AsyncTask<FlashCardPersonal, Void, Void>{
        private FlashCardPersonalDao flashCardPersonalDao;

        private UpdateFlashCardPersonalAsync(FlashCardPersonalDao flashCardPersonalDao ){
            this.flashCardPersonalDao = flashCardPersonalDao;
        }

        @Override
        protected Void doInBackground(FlashCardPersonal... flashCardPersonals) {
            flashCardPersonalDao.update(flashCardPersonals[0]);
            return null;
        }
    }
    private static class DeleteFlashCardPersonalAsync extends AsyncTask<FlashCardPersonal, Void, Void>{
        private FlashCardPersonalDao flashCardPersonalDao;

        private DeleteFlashCardPersonalAsync(FlashCardPersonalDao flashCardPersonalDao ){
            this.flashCardPersonalDao = flashCardPersonalDao;
        }

        @Override
        protected Void doInBackground(FlashCardPersonal... flashCardPersonals) {
            flashCardPersonalDao.delete(flashCardPersonals[0]);
            return null;
        }
    }

    // Question
    //Methods to do db operations
    public void insertQuestion(Question question){
        new InsertQuestionAsync(questionDao).execute(question);
    }
    public void updateQuestion(Question question){
        new UpdateQuestionAsync(questionDao).execute(question);
    }
    public void deleteQuestion(Question question){
        new DeleteQuestionAsync(questionDao).execute(question);
    }
    public LiveData<List<Question>> getAllQuestion(){
        return allQuestion;
    }

    //Androind needs a Async data operation so ...
    //
    private static class InsertQuestionAsync extends AsyncTask<Question, Void, Void>{
        private QuestionDao questionDao;

        private InsertQuestionAsync(QuestionDao questionDao ){
            this.questionDao = questionDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            questionDao.insert((questions[0]));
            return null;
        }
    }
    private static class UpdateQuestionAsync extends AsyncTask<Question, Void, Void>{
        private QuestionDao questionDao;

        private UpdateQuestionAsync(QuestionDao questionDao ){
            this.questionDao = questionDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            questionDao.update((questions[0]));
            return null;
        }
    }
    private static class DeleteQuestionAsync extends AsyncTask<Question, Void, Void>{
        private QuestionDao questionDao;

        private DeleteQuestionAsync(QuestionDao questionDao ){
            this.questionDao = questionDao;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            questionDao.delete((questions[0]));
            return null;
        }
    }
}
