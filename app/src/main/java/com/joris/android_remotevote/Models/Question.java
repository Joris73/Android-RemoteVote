package com.joris.android_remotevote.Models;

import java.util.ArrayList;

/**
 * Created by jobos on 04/02/2016.
 */
public class Question {
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
}
