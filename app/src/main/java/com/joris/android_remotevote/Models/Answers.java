package com.joris.android_remotevote.Models;

/**
 * Created by jobos on 04/02/2016.
 */
public class Answers {
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
}
