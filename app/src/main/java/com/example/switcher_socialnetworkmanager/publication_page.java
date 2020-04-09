package com.example.switcher_socialnetworkmanager;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class publication_page extends FragmentActivity implements View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    Button btn_publi;
    Button btn_settings;
    Button btn_account;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager2 and a PagerAdapter.

        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        btn_account = (Button) findViewById(R.id.btn_account);
        btn_publi = (Button) findViewById(R.id.btn_publi);
        btn_settings = (Button) findViewById(R.id.btn_settings);
    }

    @Override
    public void onBackPressed() {
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
            return new ScreenSlidePageFragment(position, viewPager);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}