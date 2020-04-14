package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

public class NouvellePublication extends AppCompatActivity {

    Button btn_publier;
    Button btn_retour_publi;
    EditText edTxt_message;
    AppCompatActivity currentApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApp=this;
        setContentView(R.layout.activity_nouvelle_publication);
        btn_publier = findViewById(R.id.btn_publier);
        btn_retour_publi = findViewById(R.id.btn_retour_publi);
        edTxt_message = findViewById(R.id.edTxt_message);
        btn_publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    String txt_message = edTxt_message.getText().toString();
                    final Intent intent = new ComposerActivity.Builder(currentApp)
                            .session(session)
                            .text(txt_message)
                            .hashtags()
                            .createIntent();
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                    Toast.makeText(NouvellePublication.this,"Vous devez d'abord être connecté !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_retour_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
