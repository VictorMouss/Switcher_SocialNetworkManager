package com.example.switcher_socialnetworkmanager;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Date;

public class Publication {

    public String textPubli;
    public Uri imageUri;
    public Date date;

    public Publication(String textPubli, Uri imageUri, Date date) {
        this.textPubli = textPubli;
        this.imageUri = imageUri;
        this.date = date;
    }

    public Publication(String textPubli, Date date) {
        this.textPubli = textPubli;
        this.imageUri = null;
        this.date = date;
    }


    @Override
    public String toString() {
        /*String dateStr = String.valueOf(date);
        String heureStr = String.valueOf(heure);
        String dateCorrecte =  ""+dateStr.charAt(0)+dateStr.charAt(1)+"/";*/
        return "Publication du ";
    }
}
