package com.example.learnwordsapp;

public class ModelClass {

    String Question;

    String oA;
    String oB;
    String ans;
    String Answer;

    public ModelClass(String question, String oA, String oB,String ans, String answer) {

        Question = question;
        this.oA = oA;
        this.oB = oB;
        this.ans = ans;
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getoA() {
        return oA;
    }

    public void setoA(String oA) {
        this.oA = oA;
    }

    public String getoB() {
        return oB;
    }

    public void setoB(String oB) {
        this.oB = oB;
    }


    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
