package com.example.groundzero.farewell;

import android.os.Parcel;
import android.os.Parcelable;

public class postI implements Parcelable {
    private String name, dod, dob, sex, description, eulogy, date, user,age , photo;

    public postI(String name, String dod, String dob, String sex, String description, String eulogy, String date,String user,String age,String photo) {
        this.name = name;
        this.dob = dob;
        this.dod = dod;
        this.sex = sex;
        this.description = description;
        this.eulogy = eulogy;
        this.date = date;
        this.user = user;
        this.age = age;
        this.photo = photo;
    }

    public postI() {

    }

    protected postI(Parcel in) {
        name = in.readString();
        dod = in.readString();
        dob = in.readString();
        sex = in.readString();
        description = in.readString();
        eulogy = in.readString();
        date = in.readString();
        user = in.readString();
        age = in.readString();
        photo = in.readString();
    }

    public static final Creator<postI> CREATOR = new Creator<postI>() {
        @Override
        public postI createFromParcel(Parcel in) {
            return new postI(in);
        }

        @Override
        public postI[] newArray(int size) {
            return new postI[size];
        }
    };

    public void setPhoto(String path){ photo = path;}

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
    public String getAge() {
        return age;
    }
    public String getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dod);
        dest.writeString(dob);
        dest.writeString(sex);
        dest.writeString(description);
        dest.writeString(eulogy);
        dest.writeString(date);
        dest.writeString(user);
        dest.writeString(age);
        dest.writeString(photo);
    }
}
