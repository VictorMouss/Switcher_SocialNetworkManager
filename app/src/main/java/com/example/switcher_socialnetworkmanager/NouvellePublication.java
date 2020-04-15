package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class NouvellePublication extends AppCompatActivity {

    Button btn_publier;
    Button btn_retour_publi;
    EditText edTxt_message;
    AppCompatActivity currentApp;
    ArrayList<Publication> listePublications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApp=this;
        setContentView(R.layout.activity_nouvelle_publication);
        final SharedPreferences prefStockées =  getSharedPreferences("mesPrefs",MODE_PRIVATE);

        btn_publier = findViewById(R.id.btn_publier);
        btn_retour_publi = findViewById(R.id.btn_retour_publi);
        edTxt_message = findViewById(R.id.edTxt_message);
        btn_publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String listPubliTxtJson = prefStockées.getString("clefListePublications","");
                if (listPubliTxtJson.equals("")){
                    listePublications = new ArrayList<Publication>();
                }
                else{
                    Publication[] tableauPublicationTemporaire = gson.fromJson(listPubliTxtJson, Publication[].class);
                    listePublications = new ArrayList<Publication>(Arrays.asList(tableauPublicationTemporaire));
                }
                String txt_message = edTxt_message.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                try {
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    final Intent intent = new ComposerActivity.Builder(currentApp)
                            .session(session)
                            .text(txt_message)
                            .hashtags()
                            .createIntent();
                    startActivity(intent);
                    Publication publicationAdd = new Publication(txt_message,currentTime);
                    listePublications.add(publicationAdd);
                    SharedPreferences.Editor prefEditor = prefStockées.edit();
                    String listePublicationEnJson = gson.toJson(listePublications);
                    prefEditor.putString("clefListePublications", listePublicationEnJson);
                    prefEditor.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                    Toast.makeText(NouvellePublication.this,"Vous devez d'abord être connecté !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_retour_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
