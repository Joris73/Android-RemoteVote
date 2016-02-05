package com.joris.android_remotevote.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jobos on 04/02/2016.
 */
public class Answers implements Parcelable {
    String content;
    boolean isGood;

    public Answers(String content, boolean isGood) {
        this.content = content;
        this.isGood = isGood;
    }

    public String getContent() {
        return content;
    }

    public boolean isGood() {
        return isGood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeByte((byte) (isGood ? 1 : 0));
    }

    public static final Parcelable.Creator<Answers> CREATOR = new Parcelable.Creator<Answers>() {
        @Override
        public Answers createFromParcel(Parcel source) {
            return new Answers(source);
        }

        @Override
        public Answers[] newArray(int size) {
            return new Answers[0];
        }
    };

    public Answers(Parcel in) {
        content = in.readString();
        isGood = in.readByte() != 0;
    }
}
