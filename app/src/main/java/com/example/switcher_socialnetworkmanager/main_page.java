package com.example.switcher_socialnetworkmanager;

import android.content.Intent;
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

public class main_page extends AppCompatActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */

    private static final int NUM_PAGES = 3;
    Button btn_publi;
    Button btn_settings;
    Button btn_account;
    Fragment frag;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    Fragment f0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bottom_buttons);

        // Instantiate a ViewPager2 and a PagerAdapter.
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
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


        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    f0 = new ScreenSlidePageFragmentConnexion();
                    return f0;
                case 1:
                    return new ScreenSlidePageFragmentPubli();
                case 2:
                default:
                    return new ScreenSlidePageFragmentParametres();
            }

        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}