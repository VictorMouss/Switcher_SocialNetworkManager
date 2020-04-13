package com.example.switcher_socialnetworkmanager;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class ScreenSlidePageFragment extends Fragment {

    int pageNumber;

    public ScreenSlidePageFragment(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView;
        switch (pageNumber) {
            case 0:
                myView = inflater.inflate(R.layout.connexion_page, container, false);
                break;
            case 1 :
                myView = inflater.inflate(R.layout.publication_page, container, false);
                break;
            case 2 :
                myView = inflater.inflate(R.layout.parametre_page, container, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pageNumber);
        }

        return myView;
    }


}
