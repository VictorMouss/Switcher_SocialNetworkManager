package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class Twitter_log_in extends AppCompatActivity {

    TwitterLoginButton loginButton;
    Button btn_retour;
    Intent currentIntent;
    AppCompatActivity currentApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentIntent=getIntent();
        currentApp=this;
        super.onCreate(savedInstanceState);

        TwitterConfig config = new TwitterConfig.Builder(this)//création de la configuration de la session
                .logger(new DefaultLogger(Log.DEBUG)) //on active l'historique de débug
                //clé d'API correspondant à notre application
                .twitterAuthConfig(new TwitterAuthConfig("FBN7F6TUIVSNgv74kn2eamDbi",   "Juo5aBRmkPFamzH4pVu3Fe6P2mRQSrl71BS800Nff66ZgtnN4e"))
                .debug(true) //on active le débugage
                .build();
        Twitter.initialize(config); //on itialise le kit avec la configuration précèdement créée

        setContentView(R.layout.activity_twitter_log_in);
        loginButton = findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                Log.i("Login", "Login successful");
                Toast.makeText(currentApp,"Vous êtes connecté à Twitter",Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("mesPrefs",MODE_PRIVATE);
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken token = session.getAuthToken();
                Long userId = session.getUserId();
                String userName = session.getUserName();

                Gson gson = new Gson();
                String authTokenJson = gson.toJson(token);
                String userIdJson = gson.toJson(userId);

                SharedPreferences.Editor prefEditor = sharedPreferences.edit();

                prefEditor.putString("cle_token",authTokenJson);
                prefEditor.putString("cle_user_id",userIdJson);
                prefEditor.putString("cle_user_name",userName);

                prefEditor.commit();

                setResult(1);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("Login", "Login not successful");
                setResult(0);
            }
        });

        btn_retour = findViewById(R.id.btn_retour);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.i("Bouton cliqué","Bouton retour cliqué");
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.i("Activity Result","Back on activity twitter login");
        loginButton.onActivityResult(requestCode,resultCode,data);
    }
}
