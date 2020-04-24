package com.example.switcher_socialnetworkmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeUtils;

public class affichage_reglage extends AppCompatActivity {

    Button btn_retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final int indiceBoutonClique = intent.getIntExtra("indiceBoutonClique", -1);
        //Log.i("indice bouton cliqué",indiceBoutonClique+"");

        setContentView(R.layout.activity_affichage_reglage);
        btn_retour = findViewById(R.id.btn_retour_publi);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView txtTopBar = findViewById(R.id.lbl_topBar);
        TextView txtAffichageReglage = findViewById(R.id.textViewAffichageReglage);

        switch (indiceBoutonClique){
            case 0 :
                txtTopBar.setText("A propos");
                txtAffichageReglage.setText(getString(R.string.str_about_text));
                break;
            case 1 :
                txtTopBar.setText("Signaler un problème");
                break;
            case 2 :
                txtTopBar.setText("Nous contacter");
                break;
        }
    }
}
