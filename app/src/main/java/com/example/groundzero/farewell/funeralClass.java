package com.example.groundzero.farewell;

public class funeralClass {
    String dateFuneral,funeralL,time,gps,city,town,mpesa,bank,paypal,deceasedN,user;

    public funeralClass(String dateFuneral, String funeralL, String time, String gps, String city, String town, String mpesa, String bank, String paypal,String deceaseedN,String user) {
        this.dateFuneral = dateFuneral;
        this.funeralL = funeralL;
        this.time = time;
        this.gps = gps;
        this.city = city;
        this.town = town;
        this.mpesa = mpesa;
        this.bank = bank;
        this.paypal = paypal;
        this.deceasedN = deceaseedN;
        this.user = user;
    }

    public funeralClass() {
    }

    public String getDateFuneral() {
        return dateFuneral;
    }

    public String getFuneralL() {
        return funeralL;
    }

    public String getTime() {
        return time;
    }

    public String getGps() {
        return gps;
    }

    public String getCity() {
        return city;
    }

    public String getTown() {
        return town;
    }

    public String getMpesa() {
        return mpesa;
    }

    public String getBank() {
        return bank;
    }

    public String getPaypal() {
        return paypal;
    }

    public String getDeceasedN() {
        return deceasedN;
    }

    public String getUser() {
        return user;
    }
}
