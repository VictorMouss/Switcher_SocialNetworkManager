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


public class ScreenSlidePageFragmentPubli extends Fragment implements View.OnClickListener {

    private SharedPreferences prefsStockees;
    Gson gson = new Gson();
    private View myView;
    private Button btn_new_publi;
    ListView lv_publication;
    ArrayList<Publication> listePublications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.publication_page, container, false);
        lv_publication = myView.findViewById(R.id.lv_publications);
        refreshPublications();
        btn_new_publi = myView.findViewById(R.id.btn_new_publi);

        btn_new_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Bouton", "Btn_new_publi cliqué");
                try {
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    if (session != null) {
                        Intent intent = new Intent(getActivity(), NouvellePublication.class);
                        startActivityForResult(intent, 1);
                    }
                    else {
                        Toast.makeText(getActivity(), "Vous devez d'abord être connecté !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Vous devez d'abord être connecté !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myView;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Activity Result", "Activity result OK. Request code : " + requestCode + " - Result code : " + resultCode);
        if (requestCode == resultCode) {
            Long lastTweetId = NouvellePublication.lastTweetId;
            Log.i("Activity Result", "last tweet id : " + Long.toString(lastTweetId));
        }
        refreshPublications();
    }

    public void refreshPublications() {
        prefsStockees = getActivity().getSharedPreferences("mesPrefs", Context.MODE_PRIVATE);

        final Gson gson = new Gson();
        String listeEtudiantTxtJson = prefsStockees.getString("cle_listePublications", "");
        if (listeEtudiantTxtJson.equals("")) {
            listePublications = new ArrayList<Publication>();
        } else {
            Publication[] tableauEtudiantsTemporaire = gson.fromJson(listeEtudiantTxtJson, Publication[].class);
            listePublications = new ArrayList<Publication>(Arrays.asList(tableauEtudiantsTemporaire));
        }

        BaseAdapter customBaseAdapter = new BaseAdapter() {
            // Return list view item count.
            @Override
            public int getCount() {
                return listePublications.size();
            }

            @Override
            public Object getItem(int i) {
                return listePublications.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int itemIndex, View itemView, ViewGroup viewGroup) {
                if (itemView == null) {
                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.cadre_item_de_liste, null);
                }

                TextView txt_message2 = (TextView) itemView.findViewById(R.id.txt_message_visuPubli);
                TextView txtDateCrea = itemView.findViewById(R.id.txt_dateCrea);
                Publication publicationsAafficher = (Publication) listePublications.get(itemIndex);

                final String textePublication = publicationsAafficher.textPubli;
                final String dateCréation = publicationsAafficher.toString();

                txt_message2.setText(textePublication);
                txtDateCrea.setText(dateCréation);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AlertDialog.Builder(getContext()).setTitle("Supprimer la publication")
                                .setMessage("Voulez-vous supprimer la publication ?")
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Publication publicationSupprime =  (Publication) getItem(itemIndex);
                                        listePublications.remove(publicationSupprime);
                                        notifyDataSetChanged();
                                        SharedPreferences.Editor prefsEditor = prefsStockees.edit();
                                        // on transforme la liste d'étudiant en format json :
                                        String ListePublicationsEnJson = gson.toJson(listePublications);
                                        // on envoie la liste (json) dans la clé cle_listeEtudiants de mesPrefs :
                                        prefsEditor.putString("cle_listePublications", ListePublicationsEnJson);

                                        prefsEditor.commit(); // on enregistre les préférences
                                    }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                        //Toast.makeText(getActivity(), "vous avez cliqué sur la publication" + textePublication, Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(getActivity(), VoirPublication.class);
                        //intent.putExtra("indexPublicationClique", itemIndex);
                        //startActivityForResult(intent, 5);


                        //Publication publicationSupprime =  (Publication) getItem(itemIndex);
                        //listePublications.remove(publicationSupprime);
                        // notifyDataSetChanged();
                    }
                });
                return itemView;
            }
        };
        lv_publication.setAdapter(customBaseAdapter);
    }
}


