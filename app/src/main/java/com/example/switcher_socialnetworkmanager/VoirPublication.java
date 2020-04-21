package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class VoirPublication extends AppCompatActivity {

    // creation des references boutons et EditText que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonRetour;
    EditText txt_message;
    TextView txtDateCrea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_etudiant);
        Intent intent = getIntent();
        int indiceEtudiant = intent.getIntExtra("indexPublicationClique", -1);

        SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String listeEtudiantTxtJson = prefsStockees.getString("cle_listeEtudiants", "");
        Publication[] tableauEtudiantsTemporaire = gson.fromJson(listeEtudiantTxtJson, Publication[].class);

        Publication publicationsVisualise = tableauEtudiantsTemporaire[indiceEtudiant];

        txt_message = findViewById(R.id.txt_message);
        txtDateCrea = findViewById(R.id.txt_dateCrea);

        txt_message.setText(publicationsVisualise.textPubli);

        String dateCréation = Integer.toString(publicationsVisualise.date.getHours())+"h";
        dateCréation = dateCréation + Integer.toString(publicationsVisualise.date.getMinutes())+"m";
        dateCréation = dateCréation + Integer.toString(publicationsVisualise.date.getSeconds())+"s";
        txtDateCrea.setText(dateCréation);

        boutonRetour = findViewById(R.id.bouton_retour);

        boutonRetour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
