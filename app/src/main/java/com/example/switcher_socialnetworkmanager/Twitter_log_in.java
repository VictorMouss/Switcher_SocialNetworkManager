package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class Twitter_log_in extends AppCompatActivity {
    /*
    Activity permettant à l'utilisateur de se connecter à Twitter
     */

    //Attributs de l'Activity
    TwitterLoginButton loginButton; //Un TwitterLoginButton est un bouton un peu particulier, ne
    // permmemttant que la connexion à Twitter depuis ce bouton. Il permet de gérér presque
    // automatiquement le résultat de la connexion.
    Button btn_retour; //bouton pour permettre à l'utilisateur de revenir sur la page de Connexions
    Intent currentIntent; //Intent actuelle, utilisée pour corriger des problème avec "this"
    AppCompatActivity currentApp;//Activity actuelle, utilisée pour corriger des problème avec "this"

    @Override
    protected void onCreate(Bundle savedInstanceState) { //méthode onCreate de l'Activity
        super.onCreate(savedInstanceState); //appel du super

        currentIntent=getIntent();//On récupère l'intent et l'Activity
        currentApp=this;

        TwitterConfig config = new TwitterConfig.Builder(this)//création de la configuration de la session
                .logger(new DefaultLogger(Log.DEBUG)) //on active l'historique de débug
                //clés correspondants à l'API (consumerKey) ainsi qu'à notre application (consumerSecret)
                .twitterAuthConfig(new TwitterAuthConfig("FBN7F6TUIVSNgv74kn2eamDbi",   "Juo5aBRmkPFamzH4pVu3Fe6P2mRQSrl71BS800Nff66ZgtnN4e"))
                .debug(true) //on active le débugage
                .build();
        Twitter.initialize(config); //on itialise le kit avec la configuration précèdement créée

        setContentView(R.layout.activity_twitter_log_in); //layout
        loginButton = findViewById(R.id.login_button); //Attribution du loginBouton
        loginButton.setCallback(new Callback<TwitterSession>() {
            //On définit dans cette méthode ce qu'il se passe en fonction du résultat de la connexion à Twitter
            @Override
            public void success(Result<TwitterSession> result) {
                //Si la connexion réussie
                Toast.makeText(currentApp,"Vous êtes connecté à Twitter",Toast.LENGTH_SHORT).show();
                //On affiche un Toast message pour en notifier l'utilisateur

                /*
                On va maintenant stocker la session en cours dans les SharedPreferences, pour permettre
                une connexion automatique sans que l'utilisateur ait besoin de se reconnecter à
                chaque fois qu'il relance l'application
                 */
                //on récupère les SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("cleConnexion",MODE_PRIVATE);
                //On récupère la session
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken token = session.getAuthToken(); //On récupère les token
                Long userId = session.getUserId();//On récupère le userId
                String userName = session.getUserName();//On récupère le userName

                Gson gson = new Gson(); //On crée un gestionnaire Gson
                String authTokenJson = gson.toJson(token); //On transforme le token en Json
                String userIdJson = gson.toJson(userId); //On transforme le userId en Json

                //On récupère l'editor des SharedPreferences
                SharedPreferences.Editor prefEditor = sharedPreferences.edit();

                //On passe à l'editor les objets nécessaires à la connexion, avec leur clé liée
                prefEditor.putString("cle_token",authTokenJson);
                prefEditor.putString("cle_user_id",userIdJson);
                prefEditor.putString("cle_user_name",userName);

                prefEditor.commit(); //On enregistre les modifications

                setResult(1); //On met le résultatObtenu à 1 (succès)
                finish(); //On finit l'Acitivity
            }

            @Override
            public void failure(TwitterException exception) {
                //Si la connexion échoue
                Toast.makeText(currentApp,"Vous n'êtes pas connecté à Twitter (ECHEC DE CONNEXION)",Toast.LENGTH_SHORT).show();
                //On affiche un Toast message pour en notifier l'utilisateur
                setResult(0);//On met le résultat à 0 (échec)
                //On ne finit pas l'Activity pour que l'utilisateur puisse retenter une connexion
            }
        });

        btn_retour = findViewById(R.id.btn_retour); //On récupère le bouton retour sur le Layout
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //Lorsqu'on clique sur le bouton, on finit l'Activity
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Lorsque l'on revient sur l'Acitivity
        super.onActivityResult(requestCode,resultCode,data);//appel du super
        loginButton.onActivityResult(requestCode,resultCode,data);
        //on appelle la méthode onActivityResult du bouton (gestion de success() et failure())
    }
}
