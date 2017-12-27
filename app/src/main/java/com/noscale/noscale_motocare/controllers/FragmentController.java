package com.noscale.noscale_motocare.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.LoginFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.RegisterFragment;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class FragmentController {

    private MainActivity activity;

    private Fragment currentFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private MenuFragment menuFragment;

    private LinearLayout activityWrapper;

    public FragmentController (MainActivity activity) {
        this.activity = activity;
        initLayout();
        initFragments();
        initTransactionFragments();
        initCurrentFragment();
    }

    private void initLayout () {
        activityWrapper = (LinearLayout) activity.findViewById(R.id.activity_wrapper);
    }

    private void initFragments () {
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        menuFragment = new MenuFragment();
    }

    private void initCurrentFragment () {
        boolean isExistedUser = (null != Auth.getInstance(activity).getUser());
        currentFragment = loginFragment;

        if (isExistedUser) {
            currentFragment = menuFragment;
        }

        showFragment(currentFragment);

    }

    private void initTransactionFragments () {

        if (!isFragmentHasBeenExisted(Global.LOGIN_TAG)) {
            putFragmentIntoLayout(loginFragment, Global.LOGIN_TAG);
        }

        if (!isFragmentHasBeenExisted(Global.REGISTER_TAG)) {
            putFragmentIntoLayout(registerFragment, Global.REGISTER_TAG);
        }

        if (!isFragmentHasBeenExisted(Global.MENU_TAG)) {
            putFragmentIntoLayout(menuFragment, Global.MENU_TAG);
        }

    }

    private boolean isFragmentHasBeenExisted (String tag) {

        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (null == fragment) {
            return false;
        }

        return true;
    }

    private void putFragmentIntoLayout (Fragment fragment, String tag) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_wrapper, fragment, tag).hide(fragment).commit();
    }

    public void showFragment (Fragment fragment) {

        if (null != currentFragment) {
            hideFragment(currentFragment);
            currentFragment = fragment;
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.show(currentFragment).commit();

        showHideActionBar(currentFragment,currentFragment.getTag());
        activityWrapper.requestLayout();

    }

    private void hideFragment (Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment).commit();
    }

    public void showHideActionBar (Fragment fragment, String optionTitle) {

        ActionBar mAction = activity.getSupportActionBar();

        String title = null == optionTitle?fragment.getTag():optionTitle;

        if (fragment instanceof LoginFragment) {
            mAction.hide();
        } else {

            boolean isBackButtonEnabled = true;

            if (fragment instanceof MenuFragment) {
                isBackButtonEnabled = !isBackButtonEnabled;
            }

            mAction.show();
            mAction.setDisplayHomeAsUpEnabled(isBackButtonEnabled);
            mAction.setTitle(title);

        }

    }

    public void onItemSelectedOption (int id) {

        switch (id) {
            case android.R.id.home :

                if (currentFragment instanceof RegisterFragment) {
                    showFragment(loginFragment);
                }

                break;
            case  R.id.notification :

                if (null == activity.getNotificationManager().getNotificationPopup()) {
                    activity.getNotificationManager().initPopup();
                }

                if (activity.getNotificationManager().getNotificationPopup().getMenu().size() > 0) {
                    activity.getNotificationManager().getNotificationPopup().show();
                }

                break;
            default:break;
        }

    }

    public LinearLayout getActivityWrapper () {
        return activityWrapper;
    }

    public LoginFragment getLoginFragment () {
        return loginFragment;
    }

    public RegisterFragment getRegisterFragment () {
        return registerFragment;
    }

    public MenuFragment getMenuFragment () {
        return menuFragment;
    }

}
