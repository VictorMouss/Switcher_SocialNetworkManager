package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class Twitter_log_in_activity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    Button btn_retour;
    View previousView;

    public Twitter_log_in_activity(View previousView){
        this.previousView=previousView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("FBN7F6TUIVSNgv74kn2eamDbi","Juo5aBRmkPFamzH4pVu3Fe6P2mRQSrl71BS800Nff66ZgtnN4e"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        setContentView(R.layout.activity_twitter_log_in);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.i("Login", "Login successful");

                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("Login", "Login not successful");
            }
        });

        btn_retour = findViewById(R.id.btn_retour);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Button btn_Twitter = previousView.findViewById(R.id.btn_Twitter);
                btn_Twitter.setForeground(getResources().getDrawable(R.drawable.logo_twitter_connect));
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        loginButton.onActivityResult(requestCode,resultCode,data);
    }
}
