package com.noscale.noscale_motocare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.noscale.noscale_motocare.controllers.MenuController;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MenuAdapter extends FragmentStatePagerAdapter {

    private MenuController controller;
    public static final int GARAGE_PAGE = 0;
    public static final int SCHEDULE_PAGE = 1;
    public static final int HISTORY_PAGE = 2;

    public MenuAdapter(FragmentManager fm, MenuController controller) {
        super(fm);
        this.controller = controller;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case GARAGE_PAGE :
                return controller.getGarageFragment();
            case SCHEDULE_PAGE :
                return controller.getScheduleFragment();
            case HISTORY_PAGE :
                return controller.getHistoryFragment();
            default:
                return controller.getGarageFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

}
