package com.joris.android_remotevote.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jobos on 04/02/2016.
 */
public class Sondage implements Parcelable {
    String _id;
    String title;
    String idSimple;
    ArrayList<Question> questions;

    public Sondage(String title, String idSimple, ArrayList<Question> questions) {
        this.title = title;
        this.idSimple = idSimple;
        this.questions = questions;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        if (title == null)
            return "";
        return title;
    }

    public String getIdSimple() {
        return idSimple;
    }

    public ArrayList<Question> getQuestions() {
        if (questions == null)
            questions = new ArrayList<>();
        return questions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(idSimple);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<Sondage> CREATOR = new Parcelable.Creator<Sondage>() {
        @Override
        public Sondage createFromParcel(Parcel source) {
            return new Sondage(source);
        }

        @Override
        public Sondage[] newArray(int size) {
            return new Sondage[0];
        }
    };

    public Sondage(Parcel in) {
        _id = in.readString();
        idSimple = in.readString();
        title = in.readString();
    }
}
