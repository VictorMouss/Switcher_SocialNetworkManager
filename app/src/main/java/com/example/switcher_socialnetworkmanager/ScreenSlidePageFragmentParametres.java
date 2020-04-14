package com.example.switcher_socialnetworkmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentParametres extends Fragment implements View.OnClickListener{


    private View myView;


    public ScreenSlidePageFragmentParametres() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.parametre_page, container, false);
        return myView;
    }

    @Override
    public void onClick(View v) {

    }
}
