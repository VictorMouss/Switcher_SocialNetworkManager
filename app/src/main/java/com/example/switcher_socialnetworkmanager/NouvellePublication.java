package com.example.switcher_socialnetworkmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class NouvellePublication extends AppCompatActivity {

    Button btn_publier;
    Button btn_retour_publi;
    EditText edTxt_message;
    AppCompatActivity currentApp;
    public static Long lastTweetId;
    ArrayList<Publication> listePublications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApp = this;
        setContentView(R.layout.activity_nouvelle_publication);

        btn_publier = findViewById(R.id.btn_publier);
        btn_retour_publi = findViewById(R.id.btn_retour_publi);
        edTxt_message = findViewById(R.id.edTxt_message);

        edTxt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTxt_message.getText().toString().equals("Que voulez-vous dire ?")) {
                    edTxt_message.setText("");
                }
            }
        });

        btn_publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_message = edTxt_message.getText().toString();
                try {
                    //on récupère la session Twitter en cours
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    final Intent intent = new ComposerActivity.Builder(currentApp) //on appelle le builder
                            .session(session) //avec la session en cours
                            .text(txt_message) //le message de notre choix
                            .hashtags()//hastags
                            .createIntent();
                    startActivityForResult(intent, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                    Toast.makeText(NouvellePublication.this, "Vous devez d'abord être connecté !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_retour_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                Bundle intentExtras = intent.getExtras();
                if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
                    // success
                    final Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
                    lastTweetId = tweetId;


                    SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String listePublicationTxtJson = prefsStockees.getString("cle_listePublications", "");
                    if (listePublicationTxtJson.equals("")) {
                        listePublications = new ArrayList<Publication>();
                    } else {
                        Publication[] tableauPublicationsTemporaire = gson.fromJson(listePublicationTxtJson, Publication[].class);
                        listePublications = new ArrayList<Publication>(Arrays.asList(tableauPublicationsTemporaire));
                    }
                    String nom = edTxt_message.getText().toString();
                    Date currentTime = Calendar.getInstance().getTime();
                    Log.i("Objet visualisé", currentTime + "");
                    Publication publicationsAjout = new Publication(nom, currentTime);
                    listePublications.add(publicationsAjout);

                    /** enregistrement de la liste dans "SharedPreferences" */
                    // on cree un éditeur de préferences, pour mettre à jour "mesPrefs" :
                    Editor prefsEditor = prefsStockees.edit();
                    // on transforme la liste d'étudiant en format json :
                    String ListePublicationsEnJson = gson.toJson(listePublications);
                    // on envoie la liste (json) dans la clé cle_listeEtudiants de mesPrefs :
                    prefsEditor.putString("cle_listePublications", ListePublicationsEnJson);

                    prefsEditor.commit(); // on enregistre les préférences

                    /** fin de l'activite, mais en renvoyant un message de type Toast */
                    Toast.makeText(NouvellePublication.this, "vous avez ajouté la publication : " + nom, Toast.LENGTH_SHORT).show();
                    currentApp.setResult(1);
                    finish();
                } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
                    // failure
                    final Intent retryIntent = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
                } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
                    // cancel
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter(TweetUploadService.UPLOAD_SUCCESS));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Activity Result Tweet", "Activity result OK. Request code : " + requestCode + " - Result code : " + resultCode);
    }


}
