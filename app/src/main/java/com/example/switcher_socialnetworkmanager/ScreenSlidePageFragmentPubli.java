package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;


public class ScreenSlidePageFragmentPubli extends Fragment implements View.OnClickListener{

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

                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                final Intent intent = new ComposerActivity.Builder(getActivity())
                        .session(session)
                        .text("Test text compo")
                        .hashtags("#Twittter")
                        .createIntent();
                startActivity(intent);
            }
        });
        return myView;
    }

    @Override
    public void onClick(View v) {

    }
}
