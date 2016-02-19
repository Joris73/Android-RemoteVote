package com.joris.android_remotevote.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jobos on 04/02/2016.
 */
public class Answers implements Parcelable {
    String _id;
    String content;
    boolean isGood;
    boolean selected = false;

    public Answers(String content, boolean isGood) {
        this.content = content;
        this.isGood = isGood;
    }

    public String getContent() {
        if (content == null)
            return "";
        return content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String get_id() {
        return _id;
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
        dest.writeString(_id);
        dest.writeString(content);
        dest.writeByte((byte) (isGood ? 1 : 0));
        dest.writeByte((byte) (selected ? 1 : 0));
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
        _id = in.readString();
        content = in.readString();
        isGood = in.readByte() != 0;
        selected = in.readByte() != 0;
    }
}
