package com.example.groundzero.farewell;

public class postI {
    private String name, dod, dob, sex, description, eulogy, date, user;

    public postI(String name, String dod, String dob, String sex, String description, String eulogy, String date,String user) {
        this.name = name;
        this.dob = dob;
        this.dod = dod;
        this.sex = sex;
        this.description = description;
        this.eulogy = eulogy;
        this.date = date;
        this.user = user;
    }

    public postI() {

    }

    public String getName() {
        return name;
    }

    public String getDod() {
        return dod;
    }

    public String getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public String getDescription() {
        return description;
    }

    public String getEulogy() {
        return eulogy;
    }
    public String getDate() {
        return date;
    }
    public String getUser() {
        return user;
    }
}
