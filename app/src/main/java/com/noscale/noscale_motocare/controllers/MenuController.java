package com.noscale.noscale_motocare.controllers;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.adapters.MenuAdapter;
import com.noscale.noscale_motocare.fragments.GarageFragment;
import com.noscale.noscale_motocare.fragments.HistoryFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.ProfileFragment;
import com.noscale.noscale_motocare.fragments.ScheduleFragment;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.listeners.MenuSelectedListener;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MenuController {

    private MenuFragment fragment;
    private TabLayout menuLayout;
    private ViewPager menuPager;
    private MenuAdapter menuAdapter;

    private GarageFragment garageFragment;
    private HistoryFragment historyFragment;
    private ScheduleFragment scheduleFragment;
    private ProfileFragment profileFragment;

    public MenuController (MenuFragment fragment) {
        this.fragment = fragment;
        initFragments();
        initLayout();
        initData();
        initEvent();
    }

    private void initFragments () {
        garageFragment = new GarageFragment();
        historyFragment = new HistoryFragment();
        scheduleFragment = new ScheduleFragment();
        profileFragment = new ProfileFragment();
    }

    private void initLayout () {
        menuLayout = (TabLayout) fragment.getView().findViewById(R.id.menu_layout);
        menuPager = (ViewPager) fragment.getView().findViewById(R.id.menu_pager);
        menuPager.setOffscreenPageLimit(3);
        menuLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initData () {
        FragmentManager fm = fragment.getActivity().getSupportFragmentManager();

        menuLayout.addTab(
                menuLayout.newTab().setText(Global.GARAGE_TAG).setIcon(R.drawable.ic_settings)
        );

        menuLayout.addTab(
                menuLayout.newTab().setText(Global.SCHEDULE_TAG).setIcon(R.drawable.ic_access_alarm)
        );

        menuLayout.addTab(
                menuLayout.newTab().setText(Global.HISTORY_TAG).setIcon(R.drawable.ic_history)
        );

        if (!((MainActivity) fragment.getActivity()).getFragmentController().isFragmentHasBeenExisted(Global.PROFILE_TAG)) {
            ((MainActivity) fragment.getActivity()).getFragmentController().putFragmentIntoLayout(profileFragment, Global.PROFILE_TAG);
        }

        menuAdapter = new MenuAdapter(fm,this);
        menuPager.setAdapter(menuAdapter);

    }

    private void initEvent () {
        menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menuLayout));
        menuLayout.addOnTabSelectedListener(new MenuSelectedListener(menuPager));
    }

    public void goToProfilePage () {
        MainActivity activity = ((MainActivity)fragment.getActivity());
        activity.getFragmentController().hideLeftDrawer();
        profileFragment.getController().setProfileText();
        profileFragment.getController().resetForm(null, null);
        activity.getFragmentController().showFragment(profileFragment);
    }

    public void clearData () {
        garageFragment.getController().getAdapter().getGarageList().clear();
        fragment.getBookingController().getScheduleAdapter().getBookingList().clear();
        fragment.getBookingController().getHistoryAdapter().getBookingList().clear();

        garageFragment.getController().getAdapter().notifyDataSetChanged();
        fragment.getBookingController().getScheduleAdapter().notifyDataSetChanged();
        fragment.getBookingController().getHistoryAdapter().notifyDataSetChanged();
    }

    /**
     * Go To another tab;
     * @param index please to look up at {@link MenuAdapter}
     */
    public void goToAnotherTab (int index) {
        menuLayout.getTabAt(index).select();
    }

    public GarageFragment getGarageFragment () {
        return garageFragment;
    }

    public HistoryFragment getHistoryFragment () {
        return historyFragment;
    }

    public ScheduleFragment getScheduleFragment () {
        return scheduleFragment;
    }

    public ProfileFragment getProfileFragment () {
        return profileFragment;
    }

}
