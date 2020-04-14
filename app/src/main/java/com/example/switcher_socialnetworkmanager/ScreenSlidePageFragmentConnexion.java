package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentConnexion extends Fragment implements View.OnClickListener{

    private Button btn_Twitter;
    private View myView;


    public ScreenSlidePageFragmentConnexion() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connexion_page, container, false);
        btn_Twitter = myView.findViewById(R.id.btn_Twitter);
        btn_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Twitter_log_in_activity.class);
                startActivity(intent);
            }
        });
        return myView;
    }

    @Override
    public void onClick(View v) {

    }

}
