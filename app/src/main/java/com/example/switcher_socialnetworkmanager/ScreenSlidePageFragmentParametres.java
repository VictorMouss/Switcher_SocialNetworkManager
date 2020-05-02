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


public class ScreenSlidePageFragmentParametres extends Fragment implements View.OnClickListener{


    private View myView;
    Button btn_about;
    Button btn_signal_prob;
    Button btn_contact;
    Button btn_disconnect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.parametre_page, container, false);
        btn_about = myView.findViewById(R.id.btn_about);
        btn_signal_prob = myView.findViewById(R.id.btn_signal_prob);
        btn_contact = myView.findViewById(R.id.btn_contact);
        btn_disconnect = myView.findViewById(R.id.btn_disconnect);



        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), affichage_reglage.class);
                intent.putExtra("indiceBoutonClique", 0);
                startActivity(intent);
            }
        });

        btn_signal_prob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), affichage_reglage.class);
                intent.putExtra("indiceBoutonClique", 1);
                startActivity(intent);
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), affichage_reglage.class);
                intent.putExtra("indiceBoutonClique", 2);
                startActivity(intent);
            }
        });


        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                if (session!=null) {
                    new AlertDialog.Builder(getContext()).setTitle("Déconnexion")
                            .setMessage("Voulez-vous vous déconnecter de tous les réseaux ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mesPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
                                    prefEditor.clear();
                                    prefEditor.commit();
                                    Toast.makeText(getActivity(), "Vous êtes déconnecté de Twitter", Toast.LENGTH_SHORT).show();
                                    ScreenSlidePageFragmentConnexion.etatConnexionTwitter = false;
                                    getActivity().recreate();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else {
                    Toast.makeText(getActivity(), "Aucune connexion en cours", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myView;
    }

    @Override
    public void onClick(View v) {

    }
}
