package com.example.groundzero.farewell;

import android.os.Parcelable;
import android.os.Parcel;


public class Memorial implements Parcelable {
   private String name,user,description,eulogy,photo, birth, death;

    public Memorial(String name, String user, String description, String eulogy, String photo,String birth, String death) {
        this.name = name;
        this.user = user;
        this.description = description;
        this.eulogy = eulogy;
        this.photo = photo;
        this.birth = birth;
        this.death = death;
    }

    public Memorial() {
    }

    protected Memorial(Parcel in) {
        name = in.readString();
        user = in.readString();
        description = in.readString();
        eulogy = in.readString();
        photo = in.readString();
        birth = in.readString();
        death = in.readString();
    }

    public static final Creator<Memorial> CREATOR = new Creator<Memorial>() {
        @Override
        public Memorial createFromParcel(Parcel in) {
            return new Memorial(in);
        }

        @Override
        public Memorial[] newArray(int size) {
            return new Memorial[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getEulogy() {
        return eulogy;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBirth() {
        return birth;
    }

    public String getDeath() {
        return death;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(user);
        dest.writeString(description);
        dest.writeString(eulogy);
        dest.writeString(photo);
        dest.writeString(birth);
        dest.writeString(death);
    }
}
