package com.example.learnwordsapp;

public class Ranking {
    private String UserName;
    private int Score;

    public Ranking() {
        UserName = "";
        Score = 0;
    }

    public Ranking(String UserName, int Score) {
        this.UserName = UserName;
        this.Score = Score;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}

