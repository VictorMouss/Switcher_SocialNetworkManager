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

public class main_page extends AppCompatActivity {
    /* Page principale, contenant les boutons de navigation en bas ainsi que le widget ViewPager2,
     * permettant de faire un défilement de Fragment avec glissement */

    private static final int NUM_PAGES = 3; //Nombre de pages, nécessaire pour le ViewPager2

    //Boutons de navigation entre les pages, en bas
    Button btn_publi;
    Button btn_settings;
    Button btn_account;

    private ViewPager2 viewPager; //Widget ViewPager2

    //Requires car la méthode setForeground nécessite une version d'API légèrement supérieure à celle définie dans le manifest
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) { //on create de l'Activity
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)//création de la configuration de la session
                .logger(new DefaultLogger(Log.DEBUG)) //on active l'historique de débug
                //clé d'API correspondant à notre application
                .twitterAuthConfig(new TwitterAuthConfig("FBN7F6TUIVSNgv74kn2eamDbi", "Juo5aBRmkPFamzH4pVu3Fe6P2mRQSrl71BS800Nff66ZgtnN4e"))
                .debug(true) //on active le débugage
                .build();
        Twitter.initialize(config); //on itialise le kit avec la configuration précèdement créée

        /*Stockage de la session dans les SharedPreferences*/
        SharedPreferences sharedPreferences = getSharedPreferences("mesPrefs", MODE_PRIVATE); //on récupère les SharedPreferences
        Gson gson = new Gson(); //Création du Gson
        /*Récupération du Token de connexion (en Json), userID (en Json) et userName*/
        String tokenJson = sharedPreferences.getString("cle_token", "");
        String userIdJson = sharedPreferences.getString("cle_user_id", "");
        String userName = sharedPreferences.getString("clef_user_name", "");
        int currentPage; //page actuelle, permet de faire changer la page actuelle au lancement de
        // l'application en fonction de la connexion à Twitter, ou non

        if (!tokenJson.equals("")) { //S'il y a déjà une session twitter dans les SharedPreferences
            /* On transforme en String le token de session l'userId grâce au Gson*/
            TwitterAuthToken token = gson.fromJson(tokenJson, TwitterAuthToken.class);
            Long userId = gson.fromJson(userIdJson, Long.class);
            TwitterSession session = new TwitterSession(token, userId, userName); //on recrée la session
            TwitterCore.getInstance().getSessionManager().setActiveSession(session);//on met la session actuelle à la session précédemment créée
            ScreenSlidePageFragmentConnexion.etatConnexionTwitter = true; //on change l'attribut statique
            currentPage = 1; //si la connexion a déjà était faite, on met l'utilisateur sur la page 1, permettant de faire une nouvelle pubication
        } else { //S'il n'y a pas de session Twitter existante dans les SharedPreferences
            ScreenSlidePageFragmentConnexion.etatConnexionTwitter = false;//on change l'attribut statique
            currentPage = 0; //on met l(utilisateur sur la page 0, permettant la connexion
        }

        setContentView(R.layout.bottom_buttons);//layout

        /*on définit le pagerAdpter et le ViewPager2 */
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);


        viewPager.setCurrentItem(currentPage); //on met la page actuelle définie précédement
        //On récupère les boutons de navigation
        btn_account = findViewById(R.id.btn_account);
        btn_publi = findViewById(R.id.btn_publi);
        btn_settings = findViewById(R.id.btn_settings);

        /* onCLickListenner des boutons de navigation : en fonction du bouton cliqué, on change
        le currentItem du ViewPager2 */

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

    }

    //Override de l'action effctuée lorsque le bouton BACK est appuyé (bouton noir présents sur les appareils Android)
    @Override
    public void onBackPressed() {
        Log.i("Back", "BackPressed");

        if (viewPager.getCurrentItem() == 1) {
            // Si l'utilisateur est sur la page principale (page de publication) alors on effectue
            // l'action "normale", via le super de la méthode
            super.onBackPressed();
        } else if (viewPager.getCurrentItem() == 0) {
            // Sinon, en fonction de la page, on revient sur la page principale (la page suivante).
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            // Sinon, en fonction de la page, on revient sur la page principale (la page précédente).
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        }
    }

    /*On va maintenant créé notre PagerAdapter, pour effectuer les actions de notre choix en
    fonction du glissement sur l'écran */

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter { //création de la sous-classe

        ScreenSlidePagerAdapter(FragmentActivity fa) { //constructeur
            super(fa); //appel du super
        }

        @Override
        public Fragment createFragment(int position) { //méthode créant les fragments en fonction de la position de la page
            switch (position) { //en fonction de l'indice de la page créée :
                case 0: //la page 0 est la page de connexion
                    return new ScreenSlidePageFragmentConnexion();
                case 1://la page 1 est la page de publication
                    return new ScreenSlidePageFragmentPubli();
                case 2://la page 2 est la page de paramètres
                default://ici default permet d'être sûr d'avoir un return
                    return new ScreenSlidePageFragmentParametres();
            }
        }

        //Requires car la méthode setForeground nécessite une version d'API légèrement supérieure à celle définie dans le manifest
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public int getItemCount() { //cette méthode permet d'avoir le nombre de pages existantes,
            //nous nous en servirons pour changer les icones des boutons en fonction de la page actuelle
            int positionActuelle = viewPager.getCurrentItem(); //récupération de la page actuellement affichée
            switch (positionActuelle) { //en fonction de la page affichée, on change les icones des boutons de navigation
                //on doit gérer les erreurs lancées par les méthodes setForeGround, qui arrivent
                // lors de l'éxécution de la méthode la première fois, lorsque que les boutons
                // ne sont pas définie (pas d'actions particulières dans le catch)
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
            return NUM_PAGES; //on return l'attribut statique (but premier de la méthode !)
        }
    }


}
