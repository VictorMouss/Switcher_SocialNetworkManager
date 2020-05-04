package com.example.switcher_socialnetworkmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.Arrays;


public class ScreenSlidePageFragmentPubli extends Fragment {
    /*
    Attributs du Fragment correspondant à la page pincipale, celle des publications
     */

    private SharedPreferences prefsStockees; //SharedPreferences pour récupérer la liste des publications
    private View myView; //View du Fragement contenant le layout a retourner à la fin de la méthode onCreate
    private Button btn_new_publi; //Bouton pour créer une nouvelle publication
    ListView lv_publication; //ListeView contenant les publications
    ArrayList<Publication> listePublications; //ArrayList des publications

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //methode onCreate
        myView = inflater.inflate(R.layout.publication_page, container, false); //création du view grâce à un inflater
        lv_publication = myView.findViewById(R.id.lv_publications); //Attribution du ListView et du bouton
        btn_new_publi = myView.findViewById(R.id.btn_new_publi);
        refreshPublications(); //On actualise le ListView grâce à la méthode refreshPublication()

        btn_new_publi.setOnClickListener(new View.OnClickListener() { //onClickListenner du bouton
            @Override
            public void onClick(View v) {
                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                //On récupère la session
                if (session != null) {
                    //Si elle existe, on crée une intent pour écrire une nouvelle Publication
                    Intent intent = new Intent(getActivity(), NouvellePublication.class);
                    startActivityForResult(intent, 1);
                        /*
                        On lance l'intent avec un résultat attendu (ici 1)
                        Lancer un intent avec un résultat attendu permet de changer l'action effectuée
                        en fonction du résultat obtenu. Ici, 1 correspond à une publication qui a
                        été effectuée, et 0 une publication qui n'a pas pu être faite (erreur ou
                        annulation)
                         */
                } else {//S'il n'y a pas de session en cours, donc l'utilisateur n'est pas connecté
                    Toast.makeText(getActivity(), "Vous devez d'abord être connecté !", Toast.LENGTH_SHORT).show();
                    //On affiche donc un message Toast pour notifier l'utilisateur
                }
            }
        });

        return myView;//on return la view créé grâce à l'inflater (la méthode onCreate d'un Fragment
        // doit nécessairement retourner cette View contenant le layout)
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //la méthode onActivityResult permet de changer l'action effectuée en fonction du résultat
        // obtenu après avoir lancé une intent avec un résultat attendu.
        super.onActivityResult(requestCode, resultCode, data); //appel du super
        if (requestCode == resultCode) { //si le résultat obtenu correspond à celui attendu (ici 1)
            //alors la publication a bien été effectuée, donc on récupère le TweetId qui a été passé
            //en attribut statique de la classe
            Long lastTweetId = NouvellePublication.lastTweetId;
        }
        refreshPublications(); //On actualise le ListView grâce à la méthode refreshPublication()
    }

    public void refreshPublications() {

        prefsStockees = getActivity().getSharedPreferences("mesPrefs", Context.MODE_PRIVATE);
        // On récupère les SharedPreferences contenant les publications
        final Gson gson = new Gson(); //Gestion de Json
        String listeEtudiantTxtJson = prefsStockees.getString("cle_listePublications", "");
        //On récupère la liste de publication en format Json
        if (listeEtudiantTxtJson.equals("")) {
            //S'il n'y a aucune publication existante, on crée une nouvelle ArrayList
            listePublications = new ArrayList<Publication>();
        } else {
            //S'il existe déjà des publications, on transforme la chaîne Json en tableau de publications
            Publication[] tableauPubliTemp = gson.fromJson(listeEtudiantTxtJson, Publication[].class);
            //On transforme ensuite ce tableau de publications sous forme d'ArrayList
            listePublications = new ArrayList<Publication>(Arrays.asList(tableauPubliTemp));
        }

        BaseAdapter customBaseAdapter = new BaseAdapter() {

            @Override
            //méthode qui return le nb d'objet dans la liste
            public int getCount() {
                return listePublications.size();
            }

            @Override
            //méthode qui return l'objet à la position entréee en paramètre
            public Object getItem(int i) {
                return listePublications.get(i);
            }

            @Override
            //méthode qui return l'id de l'objet à la position entrée en paramètre
            public long getItemId(int i) {
                return i;
            }

            @Override
            //méthode permettant de construire l'affichage (itemView) de l'item associé à la position itemIndex
            public View getView(final int itemIndex, View itemView, ViewGroup viewGroup) {
                if (itemView == null) {
                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.cadre_item_de_liste, null);
                }

                /*
                On récupère les éléments du layout cadre_item_de_liste :
                - Le message de la publication (TextView)
                - La date de la publicaiton (TextView)
                 */
                TextView txt_message_visuPubli = itemView.findViewById(R.id.txt_message_visuPubli);
                TextView txtDateCrea = itemView.findViewById(R.id.txt_dateCrea);

                //On récupère la publication a afficher, pour récupérer ses attributs
                Publication publicationsAafficher = listePublications.get(itemIndex);

                final String textePublication = publicationsAafficher.textPubli; //texte de la publication
                final String dateCréation = publicationsAafficher.toString();//date de la publication

                //On définit le textes correspondants
                txt_message_visuPubli.setText(textePublication);
                txtDateCrea.setText(dateCréation);

                itemView.setOnClickListener(new View.OnClickListener() {
                    //Lorsque l'on clique sur une publication, il nous est proposé de supprimer ladite publication
                    @Override
                    public void onClick(View view) {
                        //Création de l'AlertDialog
                        new AlertDialog.Builder(getContext()).setTitle("Supprimer la publication")//Titre
                                .setMessage("Voulez-vous supprimer la publication ?")//Message
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    //Action effectuée lorsque l'utilisateur valide
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //On crée une publication correspondant à la publication qui
                                        // va être supprimée, pour la retirer de l'ArrayList
                                        Publication publicationSupprime = (Publication) getItem(itemIndex);
                                        listePublications.remove(publicationSupprime);
                                        //on notifie la ListView que l'ArrayList a été modifiée, pour rafraîchir l'affichage
                                        notifyDataSetChanged();
                                        //On récupère l'editor des SharedPreferences
                                        SharedPreferences.Editor prefsEditor = prefsStockees.edit();
                                        // on transforme la liste de publications en format Json
                                        String ListePublicationsEnJson = gson.toJson(listePublications);
                                        // on envoie la liste (json) dans la clé cle_listePublications de mesPrefs :
                                        prefsEditor.putString("cle_listePublications", ListePublicationsEnJson);

                                        prefsEditor.commit(); // on enregistre les préférences
                                    }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    //Action effectuée lorsque l'utilisateur annule
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //on ne fait rien
                                    }
                                })
                                .show(); // On affiche l'AlertDialog
                    }
                });
                return itemView; // on termine la méthode surchargée en renvoyant la view créée
            }
        };
        lv_publication.setAdapter(customBaseAdapter); //On associe la ListView au BaseAdpter qu'on vient de créer
    }
}


