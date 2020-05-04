package com.example.switcher_socialnetworkmanager;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class Twitter_log_out extends AppCompatActivity {
    /*
    Activity permettant à l'utilisateur de se connecter à Twitter
    */

    //Attributs de l'Activity
    //Boutons de retour à la page de Connexions et de Déconnexion
    Button btn_retour;
    Button btn_logout;
    AppCompatActivity currentApp;//Activity actuelle, utilisée pour corriger des problème avec "this"

    @Override
    protected void onCreate(Bundle savedInstanceState) {//méthode onCreate de l'Activity
        super.onCreate(savedInstanceState);//appel du super
        currentApp = this;//On récupère l'Acivity
        setContentView(R.layout.activity_twitter_log_out); //layout
        //On récupère les boutons du layout
        btn_retour = findViewById(R.id.btn_retour);
        btn_logout = findViewById(R.id.btn_logout);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Si on clique sur le bouton retour, on met le résultat à 0 (pas de déconnexion)
                setResult(0);
                finish(); //On finit l'Activity
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si on clique sur le bouton pour se déconnecter

                //On récupère la session en cours
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                TwitterCore.getInstance().getSessionManager().clearActiveSession(); //On supprime la session en cours

                //On récupère les SharedPreferences associés à "cleConnexion" et on les supprime
                SharedPreferences sharedPreferences = getSharedPreferences("cleConnexion", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPreferences.edit();
                prefEditor.clear();
                prefEditor.commit();

                Toast.makeText(currentApp, "Vous êtes déconnecté de Twitter", Toast.LENGTH_SHORT).show();
                //On en notifie l'utilisateur
                setResult(1); //On met le résultat à 1 (déconnexion réussie)

                finish(); //On finit l'Acitivity
            }
        });
    }
}