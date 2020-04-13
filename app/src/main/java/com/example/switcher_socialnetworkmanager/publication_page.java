package com.example.switcher_socialnetworkmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class publication_page extends FragmentActivity implements View.OnClickListener {
    /**
     *
     * The number of pages (wizard steps) to show in this demo.
     */

    private static final int NUM_PAGES = 3;
    Button btn_publi;
    Button btn_settings;
    Button btn_account;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

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
        public ScreenSlidePageFragment createFragment(int position) {
            return new ScreenSlidePageFragment(position);
        }

        @Override
        public int getItemCount() {
            Log.i("createFrag","created1");
            return NUM_PAGES;
        }

    }
}