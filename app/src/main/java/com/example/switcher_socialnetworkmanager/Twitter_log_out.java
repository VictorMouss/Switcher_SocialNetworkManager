package com.example.switcher_socialnetworkmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class Twitter_log_out extends AppCompatActivity {

    Button btn_retour;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_log_out);
        btn_retour = findViewById(R.id.btn_retour);
        btn_logout = findViewById(R.id.btn_logout);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.i("Bouton cliqué","Bouton retour cliqué");
                setResult(0);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                setResult(1);
                finish();
            }
        });
    }
}
