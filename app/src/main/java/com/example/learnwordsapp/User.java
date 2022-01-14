package com.example.learnwordsapp;
public class User {
    private String UserName;
    private String Email;

    public User(){
        UserName="";
        Email="";
    }

    public User(String UserName, String Email){
        this.UserName=UserName;
        this.Email=Email;
    }

    //region "Getters  & Setters"
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    //endregion
}
