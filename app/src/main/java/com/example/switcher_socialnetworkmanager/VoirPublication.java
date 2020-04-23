package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class VoirPublication extends AppCompatActivity {

    // creation des references boutons et EditText que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonRetour;
    Button boutonSupprimerPubli;
    EditText txt_message;
    TextView txtDateCrea;
    ArrayList<Publication> listePublications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_publication);
        Intent intent = getIntent();
        final int indiceEtudiant = intent.getIntExtra("indexPublicationClique", -1);

        final SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
        final Gson gson = new Gson();
        final String listePublicationTxtJson = prefsStockees.getString("cle_listePublications", "");
        Publication[] tableauPublicationsTemporaire = gson.fromJson(listePublicationTxtJson, Publication[].class);

        Publication publicationsVisualise = tableauPublicationsTemporaire[indiceEtudiant];
        listePublications = new ArrayList<Publication>(Arrays.asList(tableauPublicationsTemporaire));

        txt_message = findViewById(R.id.txt_message);
        txtDateCrea = findViewById(R.id.txt_dateCrea);

        txt_message.setText(publicationsVisualise.textPubli);
        Log.i("Objet visualisé", publicationsVisualise.date+"");
        String dateCréation = Integer.toString(publicationsVisualise.date.getHours())+"h";
        dateCréation = dateCréation + Integer.toString(publicationsVisualise.date.getMinutes())+"m";
        dateCréation = dateCréation + Integer.toString(publicationsVisualise.date.getSeconds())+"s";
        txtDateCrea.setText(dateCréation);

        boutonRetour = findViewById(R.id.bouton_retour);
        boutonSupprimerPubli = findViewById(R.id.bouton_supprimer_publication);

        boutonRetour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        boutonSupprimerPubli.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Publication publiSupp = (Publication) listePublications.get(indiceEtudiant);
                listePublications.remove(publiSupp);
                SharedPreferences.Editor prefsEditor = prefsStockees.edit();
                String ListePublicationsEnJson = gson.toJson(listePublications);
                prefsEditor.putString("cle_listePublications", ListePublicationsEnJson);
                prefsEditor.commit();
                finish();
            }
        });
    }
}
