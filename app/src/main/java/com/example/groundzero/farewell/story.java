package com.example.groundzero.farewell;

public class story {
   private String deceased,userEmail,date,story;

    public story() {
    }

    public story(String deceased, String userEmail, String date, String story) {
        this.deceased = deceased;
        this.userEmail = userEmail;
        this.date = date;
        this.story = story;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDate() {
        return date;
    }

    public String getStory() {
        return story;
    }
}
