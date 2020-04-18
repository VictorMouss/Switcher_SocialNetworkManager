package com.example.switcher_socialnetworkmanager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AjoutPublication extends AppCompatActivity {

    // creation des references boutons et EditText que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonRetour;
    Button boutonAjouter;
    EditText texteNomSaisi;//edTxt_message

    // listeEtudiants est l'arrayList dans laquelle on charge les objets étudiants de notre appli
    // la reference est déclarée ici, mais sera initialisée plus tard
    ArrayList<Publications> listestudiants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* les instructions "classiques" : a la création d'une activité, on appelle le constructeur
        parent et on charge le calque associé */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_etudiant);

        /*********************************/
        /*** REFERENCES VERS LE CALQUE ***/
        /*********************************/
        // association de nos references avec les objets du layout
        // les boutons permettent d'ajouter ou de revenir en arriere
        boutonAjouter = findViewById(R.id.bouton_ajout);
        // les editText permettent la saisie des informations d'ajout de l'étudiant
        texteNomSaisi = findViewById(R.id.input_message);
        texteNomSaisi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (texteNomSaisi.getText().toString().equals("Name"))
                    texteNomSaisi.setText("");
            }
        });

        /*********************************/
        /*** GESTION DU BOUTON  RETOUR ***/
        /*********************************/
        // recherche du bouton retour dans le layout
        boutonRetour = findViewById(R.id.bouton_retour);
        // ajout de l'écouteur sur le widget bouton_retour, pour revenir à l'activite précedente
        boutonRetour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*********************************/
        /*** GESTION DU BOUTON AJOUTER ***/
        /*********************************/
        /** quand on clique sur le bouton ajouter, on considere que l'on a toutes les informatioons
         * pour creer un nouvel étudiant. on va charger la liste d'étudiants issus de SharedPreferences
         * puis on va lui ajouter le nouvel etudiant, ensuite on enregistre cette liste dans
         * sharedPreferences. Enfin on quitte l'activité pour revenir au menu principal
         **/
        boutonAjouter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                /** chargement de la liste d'étudiants **/
                // on récupère les préférences stockées sous la clé mesPrefs :
                SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
                Gson gson = new Gson(); // on crée un gestionnaire de format json
                // on extrait la liste referencée par le mot cle_listeEtudiants qu'on avait stocké dans les
                // préférences partagées
                String listeEtudiantTxtJson = prefsStockees.getString("cle_listeEtudiants", "");
                // desormais dans listeEtudiantsTxtJson on a tous nos etudiants stockés dans un format json
                // on reconstruit un tableau d'objets de type étudiants grace à al liste au format json
                if (listeEtudiantTxtJson.equals("")) {
                    listestudiants = new ArrayList<Publications>();
                }
                else {
                    Publications[] tableauEtudiantsTemporaire = gson.fromJson(listeEtudiantTxtJson, Publications[].class);
                    // reconstitution d'une arrayList a partir du tableau tableauEtudiantsTemporaire
                    listestudiants = new ArrayList<Publications>(Arrays.asList(tableauEtudiantsTemporaire));
                }
                /** creation d'un nouvel étudiant **/
                String nom = texteNomSaisi.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                Publications publicationsAjout = new Publications(nom,currentTime);

                /** ajout de l'étudiant à l'arrayList contenant les étudiants */
                listestudiants.add(publicationsAjout);

                /** enregistrement de la liste dans "SharedPreferences" */
                // on cree un éditeur de préferences, pour mettre à jour "mesPrefs" :
                Editor prefsEditor = prefsStockees.edit();
                // on transforme la liste d'étudiant en format json :
                String ListeEtudiantsEnJson = gson.toJson(listestudiants);
                // on envoie la liste (json) dans la clé cle_listeEtudiants de mesPrefs :
                prefsEditor.putString("cle_listeEtudiants", ListeEtudiantsEnJson);
                prefsEditor.commit(); // on enregistre les préférences

                /** fin de l'activite, mais en renvoyant un message de type Toast */
                Toast.makeText(AjoutPublication.this, "vous avez ajouté la publication : " + nom, Toast.LENGTH_SHORT).show();
                finish(); // on ferme l'activite et on revient à l'activite precedente
            }
        });

    }

}
