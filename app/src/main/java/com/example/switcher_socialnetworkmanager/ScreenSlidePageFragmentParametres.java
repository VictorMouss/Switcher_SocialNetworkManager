package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


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

        return myView;

    }

    @Override
    public void onClick(View v) {

    }
}
