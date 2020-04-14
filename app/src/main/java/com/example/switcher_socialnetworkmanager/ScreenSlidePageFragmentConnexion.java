package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


public class ScreenSlidePageFragmentConnexion extends Fragment implements View.OnClickListener {

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
                Intent intent = new Intent(getActivity(), Twitter_log_in.class);
                startActivityForResult(intent, 1);
            }
        });
        return myView;
    }

    @Override
    public void onClick(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Activity Result", "Activity result OK. Request code : " + requestCode + " - Result code : " + resultCode);
        if (requestCode==1)
        switch (resultCode) {
            case 0 :
                Log.i("Activity Result","activity result - retour button pressed");
                break;
            case 1:
                btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_connect));
                Log.i("Activity Result","activity result - connexion OK");
                btn_Twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Twitter_log_out.class);
                        startActivityForResult(intent, 2);
                    }
                });
                break;
            case 2 :
                btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_disconnect));
                Log.i("Activity Result","activity result - not connected");
                break;
        }
        else if(requestCode==2){
            switch (resultCode){
                case 0 :
                    Log.i("Activity Result","activity result - retour button pressed");
                    break;
                case 1 :
                    Log.i("Activity Result","Disconnected");
                    btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_disconnect));
                    btn_Twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Twitter_log_in.class);
                            startActivityForResult(intent,1);
                        }
                    });
            }
        }
    }

}
