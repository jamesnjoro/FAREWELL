package com.example.groundzero.farewell;

public class user {
    private String name,email,gender,location;

    public user(String name, String email, String gender, String location) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.location = location;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getGender() {
        return gender;
    }


    public String getLocation() {
        return location;
    }


}
