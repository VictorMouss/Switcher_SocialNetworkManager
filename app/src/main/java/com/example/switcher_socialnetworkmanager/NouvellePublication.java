package com.example.switcher_socialnetworkmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    /*
    * Cette Activity est ouverte pour permettre l'écriture d'une nouvelle publication
     */
    //Attributs de l'Activity
    Button btn_publier; //Publier
    Button btn_retour_publi; //Retour à la page principale
    EditText edTxt_message; //Texte de la publication
    AppCompatActivity currentApp; //Activity en cours
    public static Long lastTweetId; //attribut statique correspond à l'ID du dernier Tweet effectué
    ArrayList<Publication> listePublications; //ArrayList contenant la liste des publications

    @Override
    protected void onCreate(Bundle savedInstanceState) {//methode onCreate appellée à la création de l'Activity
        super.onCreate(savedInstanceState);//appel du super
        currentApp = this; //on attribue currentApp
        setContentView(R.layout.activity_nouvelle_publication); //layout

        //On récupère les éléments du layout
        btn_publier = findViewById(R.id.btn_publier);
        btn_retour_publi = findViewById(R.id.btn_retour_publi);
        edTxt_message = findViewById(R.id.edTxt_message);

        edTxt_message.setOnClickListener(new View.OnClickListener() {//Lorsque l'on clique sur le texte de la publication
            @Override
            public void onClick(View v) {
                //Si le message est celui de base ("Que voulez-vous dire ?") alors on le supprime
                //pour que l'utilisateur puisse rentrer le message de son choix sans avoir besoin
                //de supprimer le texte entré initialement automatiquement
                if (edTxt_message.getText().toString().equals("Que voulez-vous dire ?")) {
                    //comparaison de la chaine de caractère et suppression
                    edTxt_message.setText("");
                }
            }
        });

        edTxt_message.setOnEditorActionListener(new TextView.OnEditorActionListener() {//lorsque l'on clique sur le bouton valider du clavier virtuel
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String txt_message = edTxt_message.getText().toString();
                //on récupère la session Twitter en cours
                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                //on appelle le builder d'un TweetComposer (natif à l'API que nous utilisons)
                final Intent intent = new ComposerActivity.Builder(currentApp)
                        .session(session) //avec la session en cours
                        .text(txt_message) //le message de notre choix
                        .hashtags()//hastags
                        .darkTheme()//avec le theme foncé
                        .createIntent();//on crée l'intent
                /*
                On va maintenant lancer l'activity contenant l'intent précédement créé, avec un
                résultat attendu (ici 1). En fonction de la réponse de Twitter à notre requête
                (en fonction de si le Tweet a été publié ou non), alors le résultat variera.
                Cette commande permet donc de ne pas enregistrer une publication qui a été refusée
                par Twitter.
                 */
                startActivityForResult(intent, 1);
                return true;
            }
        });

        btn_publier.setOnClickListener(new View.OnClickListener() { //lorsque l'on clique sur le bouton publier
            //(même action que lorsque l'on clique sur le bouton valider du clavier virtuel)
            @Override
            public void onClick(View v) {
                String txt_message = edTxt_message.getText().toString();
                //on récupère la session Twitter en cours
                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                //on appelle le builder d'un TweetComposer (natif à l'API que nous utilisons)
                final Intent intent = new ComposerActivity.Builder(currentApp)
                        .session(session) //avec la session en cours
                        .text(txt_message) //le message de notre choix
                        .hashtags()//hastags
                        .darkTheme()//avec le theme foncé
                        .createIntent();//on crée l'intent
                 /*
                On va maintenant lancer l'activity contenant l'intent précédement créé, avec un
                résultat attendu (ici 1). En fonction de la réponse de Twitter à notre requête
                (en fonction de si le Tweet a été publié ou non), alors le résultat variera.
                Cette commande permet donc de ne pas enregistrer une publication qui a été refusée
                par Twitter.
                 */
                startActivityForResult(intent, 1);
            }
        });

        btn_retour_publi.setOnClickListener(new View.OnClickListener() { //lorsque le bouton retour est cliqué
            @Override
            public void onClick(View v) {
                finish(); //on ferme l'Activity en cours et on revient donc à la page principale
            }
        });

        /*
        On va utiliser un BroadcastReceiver pour adapter l'action effectué en fonction du résultat
        de l'intent de publication d'un Tweet. En effet, il est possible qu'un Tweet ne se publie pas,
        si trop de Tweet sont fait à la seconde par un utilisateur etc. (pour eviter les bots), et donc
        qu'il soit refusé par Twitter.
         */
        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) { //méthode appelée lorsque l'intent se finit
                Bundle intentExtras = intent.getExtras(); //on récupère les extras
                if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) { //si le Tweet a été publié
                    final Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID); //on récupère l'ID dudit Tweet
                    lastTweetId = tweetId; //on le passe en attribut statique
                    /*
                    On va maintenant récupérer les SharedPreferences pour pouvoir stocker la publication
                    qui vient d'être effectuée. Pour se faire, on va recréer la liste des publications
                    existantes (s'il y en a une) grâce à un Gson (la liste étant stockée en Json),
                    dans l'attribut du type ArrayList, puis on va ajouter la publication qui vient
                    d'être faite à cette ArrayList et enfin restocker la liste dans les SharedPreferences
                     */
                    //On récupère les SharedPrefences
                    SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
                    Gson gson = new Gson(); //On crée le Gson
                    //On récupère la liste de publication dans la clé "cle_listePublications"
                    String listePublicationTxtJson = prefsStockees.getString("cle_listePublications", "");
                    if (listePublicationTxtJson.equals("")) { //si la liste est vide
                        listePublications = new ArrayList<Publication>(); //On crée une nouvelle ArrayList
                    } else { //si elle n'est pas vide
                        //On crée un tableau de pubications à grâce au Gson depuis la liste au format Json
                        Publication[] tableauPublicationsTemporaire = gson.fromJson(listePublicationTxtJson, Publication[].class);
                        //On recrée l'ArrayList à partir de ce tableau de publications
                        listePublications = new ArrayList<Publication>(Arrays.asList(tableauPublicationsTemporaire));
                    }
                    String nom = edTxt_message.getText().toString(); //On récupère le texte de la nouvelle publication
                    Date currentTime = Calendar.getInstance().getTime(); //On récupère le temps actuel du téléphone
                    Publication publicationsAjout = new Publication(nom, currentTime); //On créé la publication
                    listePublications.add(publicationsAjout);//On l'ajoute dans l'ArrayList

                    // on cree un éditeur de préferences, pour mettre à jour "mesPrefs" :
                    Editor prefsEditor = prefsStockees.edit();
                    // on transforme la liste de publications en format json :
                    String ListePublicationsEnJson = gson.toJson(listePublications);
                    // on envoie la liste (json) dans la clé cle_listePublications de mesPrefs :
                    prefsEditor.putString("cle_listePublications", ListePublicationsEnJson);

                    prefsEditor.commit(); // on enregistre les préférences

                    /** fin de l'activite, mais en renvoyant un message de type Toast */
                    Toast.makeText(NouvellePublication.this, "vous avez publié la publication : " + nom, Toast.LENGTH_SHORT).show();
                    currentApp.setResult(1); //on met le résultat correspondant à une publication réussie
                    finish();//On finit l'Activity
                } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
                    // En cas d'échec, on récupère l'intent
                    final Intent retryIntent = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
                } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
                    // En cas d'annulation du Tweet dans le TweetComposer
                }
            }
        };
        //On enregistre le broadcast receiver à notre application
        registerReceiver(broadcast_reciever, new IntentFilter(TweetUploadService.UPLOAD_SUCCESS));
    }

}
