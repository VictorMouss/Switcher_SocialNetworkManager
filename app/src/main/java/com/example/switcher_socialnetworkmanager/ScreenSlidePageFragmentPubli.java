package com.example.switcher_socialnetworkmanager;

import android.content.Context;
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
        SharedPreferences prefsStockees = getActivity().getSharedPreferences("mesPrefs", Context.MODE_PRIVATE);
        prefsStockees.edit().remove("cle_listePublications");
        refreshPublications();
        btn_new_publi = myView.findViewById(R.id.btn_new_publi);

        btn_new_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    if (session!=null) {
                        Intent intent = new Intent(getActivity(), NouvellePublication.class);
                        startActivityForResult(intent, 1);
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
        SharedPreferences prefsStockees = getActivity().getSharedPreferences("mesPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
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

                TextView txt_message2 = (TextView) itemView.findViewById(R.id.txt_message2);
                Publication publicationsAafficher = (Publication) listePublications.get(itemIndex);

                final String textePublication = publicationsAafficher.textPubli;
                txt_message2.setText(textePublication);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getActivity(), "vous avez cliqué sur la publication" + textePublication, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), VoirPublication.class);
                        intent.putExtra("indexPublicationClique", itemIndex);
                        startActivity(intent);


                        /*Publications publicationsSupprime =  (Publications) getItem(itemIndex);
                        // on affiche un petit message de type Toast, qui annonce l'étudaint supprimé
                        Toast.makeText(SupprimerPublication.this, "vous avez supprimé la publication" + publicationsSupprime.nom, Toast.LENGTH_SHORT).show();
                        // on enleve l'étudiant de la liste;
                        listestudiants.remove(publicationsSupprime);
                        /* important : on annonce a la listview que la liste d'étudiants a partir de laquelle
                        elle avait été construire a changé. ca va permettre de raffraichir l'affichage */
                        notifyDataSetChanged();
                    }
                });
                return itemView;
            }
        };
        lv_publication.setAdapter(customBaseAdapter);
    }
}


