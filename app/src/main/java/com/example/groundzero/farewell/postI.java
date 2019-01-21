package com.example.groundzero.farewell;

public class postI {
    private String name,dod,dob,sex,description,eulogy;

    public postI(String name, String dod, String dob, String sex, String description, String eulogy) {
        this.name = name;
        this.dob = dob;
        this.dod = dod;
        this.sex = sex;
        this.description = description;
        this.eulogy = eulogy;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEulogy(String eulogy) {
        this.eulogy = eulogy;
    }
}
