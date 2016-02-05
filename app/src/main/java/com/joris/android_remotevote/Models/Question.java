package com.joris.android_remotevote.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jobos on 04/02/2016.
 */
public class Question implements Parcelable {
    String content;
    int timeToAnswer;
    ArrayList<Answers> answers;

    public Question(String content, int timeToAnswer, ArrayList<Answers> answers) {
        this.content = content;
        this.timeToAnswer = timeToAnswer;
        this.answers = answers;
    }

    public String getContent() {
        return content;
    }

    public int getTimeToAnswer() {
        return timeToAnswer;
    }

    public ArrayList<Answers> getAnswers() {
        if (answers == null)
            answers = new ArrayList<>();
        return answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeInt(timeToAnswer);
        dest.writeTypedList(answers);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[0];
        }
    };

    public Question(Parcel in) {
        content = in.readString();
        timeToAnswer = in.readInt();
        answers = in.createTypedArrayList(Answers.CREATOR);
    }
}
