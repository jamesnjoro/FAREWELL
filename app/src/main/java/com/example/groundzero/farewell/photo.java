package com.example.groundzero.farewell;

public class photo {
    private String deceased,userEmail,path,date,description;

    public photo() {
    }

    public photo(String deceased, String userEmail, String path, String date, String description) {
        this.deceased = deceased;
        this.userEmail = userEmail;
        this.path = path;
        this.date = date;
        this.description = description;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPath() {
        return path;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
