package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentConnexion extends Fragment {

    /*
    Attributs du fragment correspondant à la page de Connexion
     */

    private Button btn_Twitter; //Bouton pour se connecter à Twitter
    private View myView; //view du Fragement contenant le layout a retourner à la fin de la méthode onCreate
    static boolean etatConnexionTwitter;//boolean permettant de savoir si on est connecté à Twitter
    // (false=déconnecté ; true = connecté)

    //Requires car la méthode setForeground dans la méthode vérifierConnexions() nécessite une
    // version d'API légèrement supérieure à celle définie dans le manifest
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //méthode onCreate du Fragment
        myView = inflater.inflate(R.layout.connexion_page, container, false); //création du view grâce à un inflater

        btn_Twitter = myView.findViewById(R.id.btn_Twitter); //Attributions du bouton pour se connecter à Twitter
        btn_Twitter.setOnClickListener(new View.OnClickListener() { //lorsque le bouton est cliqué
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Twitter_log_in.class); //on créé l'intent de la page de connexion à Twitter
                /*
                On va maintenant lancer l'intent avec un résultat attendu (ici 1). Cette méthode
                nous permet donc de vérifier si la connexion a pu se faire. Si la connexion a lieu,
                alors on modifiera l'icone du boutton de Twitter dans la page "Connexions", et sinon
                on ne la modifiera pas.
                 */
                startActivityForResult(intent, 1);

            }
        });
        vérifierConnexions(); //méthode permettant de mettre à jour les icones des boutonsainsi
        // que l'action a effectuer lorsqu'on clique sur les boutons
        return myView; //on return la view créé grâce à l'inflater (la méthode onCreate d'un Fragment
        // doit nécessairement retourner cette View contenant le layout)
    }

    //Requires car la méthode setForeground dans la méthode vérifierConnexions() nécessite une
    // version d'API légèrement supérieure à celle définie dans le manifest
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //En fonction du résultat lorsqu'une intent est fermée, on va déterminer les actions à effectuer
        super.onActivityResult(requestCode, resultCode, data); //appel du super
        /*
        Request code = 1 -> Tentative de connexion
            Result code = 0 -> Connexion échouée
            Result code = 1 -> Connexion réussie
        Requestion code = 2 -> Tentative de déconnexion
            Result code = 0 -> Déconnexion échouée
            Result code = 1 -> Déconnexion réussie
         */
        if (requestCode == 1) {
            if (resultCode == 1) {
                etatConnexionTwitter = true; //on met l'attribut à true (connecté)
            } else etatConnexionTwitter = false; //on met l'attribut à false (déconnecté)
        } else if (requestCode == 2 && resultCode == 1) {
            etatConnexionTwitter = false; //on met l'attribut à false (déconnecté)
        }
        vérifierConnexions();
        ; //méthode permettant de mettre à jour les icones des boutons ainsi
        // que l'action a effectuer lorsqu'on clique sur les boutons
    }

    //Requires car la méthode setForeground nécessite une version d'API légèrement supérieure à celle définie dans le manifest
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void vérifierConnexions() {
        if (etatConnexionTwitter == false) { //si on n'est pas connecté à Twitter
            // On change le logo en fonction
            btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_disconnect));
            // On change le listenner : on lance l'intent pour se connecter
            btn_Twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Twitter_log_in.class);
                    startActivityForResult(intent, 1);
                }
            });
        } else { //si on est connecté à Twitter
            // On change le logo en fonction
            btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_connect));
            // On change le listenner : on lance l'intent pour se déconnecter
            btn_Twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Twitter_log_out.class);
                    startActivityForResult(intent, 2);
                }
            });
        }
    }
}
