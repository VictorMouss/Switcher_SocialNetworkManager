package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class main_page extends AppCompatActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */


    private static final int NUM_PAGES = 3;

    Button btn_publi;
    Button btn_settings;
    Button btn_account;

    private ViewPager2 viewPager;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)//création de la configuration de la session
                .logger(new DefaultLogger(Log.DEBUG)) //on active l'historique de débug
                //clé d'API correspondant à notre application
                .twitterAuthConfig(new TwitterAuthConfig("FBN7F6TUIVSNgv74kn2eamDbi", "Juo5aBRmkPFamzH4pVu3Fe6P2mRQSrl71BS800Nff66ZgtnN4e"))
                .debug(true) //on active le débugage
                .build();
        Twitter.initialize(config); //on itialise le kit avec la configuration précèdement créée
        SharedPreferences sharedPreferences = getSharedPreferences("mesPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String tokenJson = sharedPreferences.getString("cle_token", "");
        String userIdJson = sharedPreferences.getString("cle_user_id", "");
        String userName = sharedPreferences.getString("clef_user_name", "");
        int currentPage;
        if (!tokenJson.equals("")) {
            TwitterAuthToken token = gson.fromJson(tokenJson, TwitterAuthToken.class);
            Long userId = gson.fromJson(userIdJson, Long.class);
            TwitterSession session = new TwitterSession(token, userId, userName);
            TwitterCore.getInstance().getSessionManager().setActiveSession(session);
            ScreenSlidePageFragmentConnexion.etatConnexionTwitter = true;
            currentPage = 1;
        } else {
            ScreenSlidePageFragmentConnexion.etatConnexionTwitter = false;
            currentPage = 0;
        }
        setContentView(R.layout.bottom_buttons);
        // Instantiate a ViewPager2 and a PagerAdapter.
        //on définit le pageAdpter et le ViewPager2
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);


        viewPager.setCurrentItem(currentPage);
        btn_account = findViewById(R.id.btn_account);
        btn_publi = findViewById(R.id.btn_publi);
        btn_settings = findViewById(R.id.btn_settings);

        btn_account.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.i("Bouton", "Btn_account cliqué");
                viewPager.setCurrentItem(0);
                btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small_down));
                btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small));
                btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small));
            }
        });

        btn_publi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.i("Bouton", "Btn_publi cliqué");
                viewPager.setCurrentItem(1);
                btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small));
                btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small_down));
                btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small));
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.i("Bouton", "Btn_settings cliqué");
                viewPager.setCurrentItem(2);
                btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small));
                btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small));
                btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small_down));
            }
        });

        btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small_down));
        btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small));
        btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small));

    }

    @Override
    public void onBackPressed() {
        Log.i("Back", "BackPressed");

        if (viewPager.getCurrentItem() == 1) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else if (viewPager.getCurrentItem() == 0) {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        }
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {


        ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ScreenSlidePageFragmentConnexion();
                case 1:
                    return new ScreenSlidePageFragmentPubli();
                case 2:
                default:
                    return new ScreenSlidePageFragmentParametres();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public int getItemCount() {
            int positionActuelle = viewPager.getCurrentItem();
            switch (positionActuelle) {
                case 0:
                    try {
                        btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small_down));
                        btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small));
                        btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small));
                        btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small_down));
                        btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        btn_account.setForeground(getResources().getDrawable(R.drawable.bouton_account_small));
                        btn_publi.setForeground(getResources().getDrawable(R.drawable.bouton_publi_small));
                        btn_settings.setForeground(getResources().getDrawable(R.drawable.bouton_settings_small_down));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


            }
            return NUM_PAGES;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("Activity Result", "Back on main activity");
    }


}
