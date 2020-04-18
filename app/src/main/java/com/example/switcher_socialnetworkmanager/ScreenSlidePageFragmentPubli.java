package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentPubli extends Fragment implements View.OnClickListener {

    private View myView;
    private Button btn_new_publi;
    Button btn_voirListePubli;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.publication_page, container, false);
        btn_new_publi = myView.findViewById(R.id.btn_new_publi);
        btn_voirListePubli = myView.findViewById(R.id.btn_voir_publications);
        btn_voirListePubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), ListePublications.class);
                startActivity(intent);
            }
        });
        //textView3 = myView.findViewById(R.id.textView3);
        btn_new_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    //if (session!=null){
                    //}
                    Intent intent = new Intent (getActivity(), AjoutPublication.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Vous devez d'abord être connecté !",Toast.LENGTH_SHORT).show();
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
        if (requestCode==resultCode){
            Long lastTweetId = NouvellePublication.lastTweetId;
            Log.i("Activity Result", "last tweet id : "+Long.toString(lastTweetId));
        }
    }
}


