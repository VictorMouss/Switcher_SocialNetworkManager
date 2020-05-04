package com.example.switcher_socialnetworkmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


public class ScreenSlidePageFragmentParametres extends Fragment {

    /*
    Attributs du Fragment correspondant à la page Paramètres
     */

    private View myView; //View du Fragement contenant le layout a retourner à la fin de la méthode onCreate
    //Les différents boutons des paramètres
    Button btn_about;
    Button btn_signal_prob;
    Button btn_contact;
    Button btn_disconnect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //méthode onCreate du Fragment
        myView = inflater.inflate(R.layout.parametre_page, container, false);//création du view grâce à un inflater
        //Attributions des boutons liés au Layout
        btn_about = myView.findViewById(R.id.btn_about);
        btn_signal_prob = myView.findViewById(R.id.btn_signal_prob);
        btn_contact = myView.findViewById(R.id.btn_contact);
        btn_disconnect = myView.findViewById(R.id.btn_disconnect);

        /*
        Pour réduire le nombre d'Activity, nous utilisons la même Activity pour les réglages, mais
        utilisée de façon "adaptative" : en fonction de la valeur que nous passons en Extra, l'intent
        crée va s'adpater et afficher différents éléments.
         */
        btn_about.setOnClickListener(new View.OnClickListener() { //lorsque que l'on clique sur le bouton "A propos"
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), affichage_reglage.class); //on crée l'intent
                intent.putExtra("indiceBoutonClique", 0); //on lui passe la veleur 0 en Extra
                startActivity(intent); //on lance l'Activity
            }
        });

        btn_signal_prob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//lorsque que l'on clique sur le bouton "Signaler un problème"
                Intent intent = new Intent(getActivity(), affichage_reglage.class);//on crée l'intent
                intent.putExtra("indiceBoutonClique", 1);//on lui passe la veleur 1 en Extra
                startActivity(intent); //on lance l'Activity
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//lorsque que l'on clique sur le bouton "Nous contacter"
                Intent intent = new Intent(getActivity(), affichage_reglage.class);//on crée l'intent
                intent.putExtra("indiceBoutonClique", 2);//on lui passe la veleur 2 en Extra
                startActivity(intent); //on lance l'Activity
            }
        });


        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//lorsque que l'on clique sur le bouton "Se déconnecter"
                //On récupère la session Twitter en cours
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                if (session != null) {
                    //s'il y a une session Twitter en cours, on crée une AlertDialog pour demander à
                    // l'utilisateur s'il veut se déconnecter
                    new AlertDialog.Builder(getContext()).setTitle("Déconnexion") //titre de l'alerte
                            .setMessage("Voulez-vous vous déconnecter de tous les réseaux ?")//message de l'alerte
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {//message du bouton pour valider
                                @Override
                                public void onClick(DialogInterface dialog, int which) { //action effectuée si l'utilisateur valide
                                    TwitterCore.getInstance().getSessionManager().clearActiveSession(); //On supprime la session en cours
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cleConnexion", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
                                    prefEditor.clear(); //On supprime les éléments de connexion des SharedPreferences (token, userid, userName)

                                    prefEditor.commit();//On met la nouvelle clé dans les SharedPreferences
                                    Toast.makeText(getActivity(), "Vous êtes déconnecté de Twitter", Toast.LENGTH_SHORT).show();
                                    //On affiche un message Toast pour confirmer a déconnection
                                    ScreenSlidePageFragmentConnexion.etatConnexionTwitter = false;
                                    //On change l'attribut static de l'autre frgment (gestion de l'icon du bouton)
                                    getActivity().recreate(); //permet le rafraîchissement de l'icon en question
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {//action effectuée si l'utilisateur annule
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //On ne fait rien
                                }
                            })
                            .show(); //On affiche l'AlertDialog
                } else {
                    Toast.makeText(getActivity(), "Aucune connexion en cours", Toast.LENGTH_SHORT).show();
                    //Si on n'est pas connecté à Twitter, alors il ne peut pas déconnecter. On affiche donc un message Toast pour notifier l'utilsateur
                }
            }
        });

        return myView;//on return la view créé grâce à l'inflater (la méthode onCreate d'un Fragment
        // doit nécessairement retourner cette View contenant le layout)
    }
}
