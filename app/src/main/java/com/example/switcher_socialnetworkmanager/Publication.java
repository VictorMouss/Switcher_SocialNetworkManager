package com.example.switcher_socialnetworkmanager;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Date;

public class Publication {

    public String textPubli;
    public Uri imageUri;
    public Date date;
    public Long tweetId;

    public Publication(String textPubli, Uri imageUri, Date date, Long tweetId) {
        this.textPubli = textPubli;
        this.imageUri = imageUri;
        this.date = date;
        this.tweetId = tweetId;
    }

    public Publication(String textPubli, Date date, Long tweetId) {
        this.textPubli = textPubli;
        this.imageUri = null;
        this.date = date;
        this.tweetId = tweetId;
    }

    public Publication(String textPubli, Date date) {
        this.textPubli = textPubli;
        this.imageUri = null;
        this.date = date;
        this.tweetId = null;
    }


    @Override
    public String toString() {
        return "Publication du ";
    }
}
