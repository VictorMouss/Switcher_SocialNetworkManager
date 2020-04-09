package com.example.switcher_socialnetworkmanager;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class ScreenSlidePageFragment extends Fragment {

    int pageNumber;
    Button btn_publi;
    Button btn_settings;
    Button btn_account;
    private ViewPager2 viewPager;

    public ScreenSlidePageFragment(int pageNumber, ViewPager2 viewPager) {
        this.pageNumber = pageNumber;
        this.viewPager = viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView;
        if (pageNumber == 0) {
            myView = inflater.inflate(R.layout.activity_connexion_page, container, false);
        } else if (pageNumber == 1) {
            myView = inflater.inflate(R.layout.activity_publication_page, container, false);
        } else {
            myView = inflater.inflate(R.layout.activity_parametre_page, container, false);
        }

        btn_publi = (Button) myView.findViewById(R.id.btn_publi);
        btn_settings = (Button) myView.findViewById(R.id.btn_settings);
        btn_account = (Button) myView.findViewById(R.id.btn_account);

        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        btn_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        return myView;
    }


}
