package com.example.switcher_socialnetworkmanager;

import android.net.Uri;

import java.util.Date;

public class Publication {
    //Classe Pulication, correspondant à une publication effectuée
    /* Attributs */
    public String textPubli; //Message de la publication
    public Uri imageUri;//Image de la publication
    public Date date;//Date de la publi
    public Long tweetId; //ID du Tweet

    /*
    Les différents constructeurs, en fonction des paramètres
     */
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
    public String toString() { //méthode toString, pour récupèrer quelques informations sur la publication
        String s = "Publication du " + (date.getDay()) + "/" + date.getMonth() + "/" + date.getYear() + " fait à " +
                date.getHours() + ":" + date.getMinutes();
        return s;

    }
}
