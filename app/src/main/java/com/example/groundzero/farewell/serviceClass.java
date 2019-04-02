package com.example.groundzero.farewell;

public class serviceClass {
   private String dateS,timeStart,timeStop,city,town,street,building,deceasedN,email,gps;

    public serviceClass(String dateS, String timeStart, String timeStop, String city, String town, String street, String building, String deceasedN,String email,String gps) {
        this.dateS = dateS;
        this. timeStart = timeStart;
        this.timeStop = timeStop;
        this.city = city;
        this.town = town;
        this.street = street;
        this.building = building;
        this.deceasedN = deceasedN;
        this.email = email;
        this.gps = gps;
    }

    public serviceClass() {
    }

    public String getDateS() {
        return dateS;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeStop() {
        return timeStop;
    }

    public String getCity() {
        return city;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getDeceasedN() {
        return deceasedN;
    }

    public String getEmail() {
        return email;
    }

    public String getGps() {
        return gps;
    }
}


