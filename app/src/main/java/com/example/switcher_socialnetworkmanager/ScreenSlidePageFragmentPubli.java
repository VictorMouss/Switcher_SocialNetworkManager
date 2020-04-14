package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentPubli extends Fragment implements View.OnClickListener {

    private View myView;
    private Button btn_new_publi;

    public ScreenSlidePageFragmentPubli() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.publication_page, container, false);
        btn_new_publi = myView.findViewById(R.id.btn_new_publi);
        btn_new_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exception management
                Intent intent = new Intent(getActivity(), NouvellePublication.class);
                startActivity(intent);
            }
        });
        return myView;
    }

    @Override
    public void onClick(View v) {

    }
}


