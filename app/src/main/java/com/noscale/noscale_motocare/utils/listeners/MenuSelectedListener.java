package com.noscale.noscale_motocare.utils.listeners;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MenuSelectedListener implements TabLayout.OnTabSelectedListener {

    private ViewPager menuPager;

    public MenuSelectedListener (ViewPager menuPager) {
        this.menuPager = menuPager;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        menuPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

