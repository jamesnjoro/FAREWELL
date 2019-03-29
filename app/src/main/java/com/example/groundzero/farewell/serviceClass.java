package com.example.groundzero.farewell;

public class serviceClass {
    String dateS,timeStart,timeStop,city,town,street,building,deceasedN;

    public serviceClass(String dateS, String timeStart, String timeStop, String city, String town, String street, String building, String deceasedN) {
        this.dateS = dateS;
        this. timeStart = timeStart;
        this.timeStop = timeStop;
        this.city = city;
        this.town = town;
        this.street = street;
        this.building = building;
        this.deceasedN = deceasedN;
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
}


