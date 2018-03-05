package com.gasmanager.viacheslav.gasmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AdviceActivity extends AppCompatActivity {
    int PAGE_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        getSupportActionBar().hide();

        ViewPager pager = findViewById(R.id.advicepager);
        FragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("tab", "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm1) {
            super(fm1);
        }

        @Override
        public Fragment getItem(int position) {
            return AdvicePager.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = {getResources().getText(R.string.advice) + " 1", getResources().getText(R.string.advice) + " 2", getResources().getText(R.string.advice) + " 3", getResources().getText(R.string.advice) + " 4", getResources().getText(R.string.advice) + " 5"};
            return titles[position];
        }

    }
}
