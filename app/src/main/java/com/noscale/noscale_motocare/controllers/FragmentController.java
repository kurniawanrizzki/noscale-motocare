package com.noscale.noscale_motocare.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.DetailGarageFragment;
import com.noscale.noscale_motocare.fragments.LoginFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.ProfileFragment;
import com.noscale.noscale_motocare.fragments.RegisterFragment;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MLoadingDialog;

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
    private MLoadingDialog dialog;
    private boolean isExistedUser;

    public FragmentController (MainActivity activity) {
        this.activity = activity;
        initLayout();
        initFragments();
        initTransactionFragments();
        initCurrentFragment();
    }

    private void initLayout () {
        activityWrapper = (LinearLayout) activity.findViewById(R.id.activity_wrapper);
        dialog = new MLoadingDialog(activity);
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
            this.isExistedUser = isExistedUser;
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

    public boolean isFragmentHasBeenExisted (String tag) {

        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (null == fragment) {
            return false;
        }

        return true;
    }

    public void putFragmentIntoLayout (Fragment fragment, String tag) {
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

            boolean isDrawerToggleNeedToHidden = true;

            if ((fragment instanceof RegisterFragment)) {
                activity.getMenu().getItem(0).setVisible(false);
            } else if ((fragment instanceof DetailGarageFragment) || (fragment instanceof ProfileFragment)) {
                isDrawerToggleNeedToHidden = !isDrawerToggleNeedToHidden;
            }

            mAction.show();
            activity.getMDrawerToggle().setDrawerIndicatorEnabled(isDrawerToggleNeedToHidden);
            mAction.setDisplayHomeAsUpEnabled(true);
            mAction.setTitle(title);

        }

    }

    public void onItemSelectedOption (int id) {

        switch (id) {
            case android.R.id.home :

                if (currentFragment instanceof RegisterFragment) {
                    showFragment(loginFragment);
                } else if ((currentFragment instanceof ProfileFragment) || (currentFragment instanceof DetailGarageFragment)) {
                    showFragment(menuFragment);
                }

                break;
            case  R.id.notification :

                hideLeftDrawer();

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

    public void hideLeftDrawer () {
        if (activity.getMDrawerLayout().isDrawerOpen(Gravity.START)) {
            activity.getMDrawerLayout().closeDrawer(Gravity.START);
        }
    }

    public boolean isEmailValidated (EditText e, String email) {

        boolean isValidated = true;

        if (email.equals(Global.DEFAULT_STRING_VALUE)) {
            isValidated = !isValidated;
            e.setError(
                    String.format(
                            activity.getString(R.string.field_required),
                            "Email"
                    )
            );
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (isValidated) {
                isValidated = !isValidated;
                e.setError(
                        String.format(
                                activity.getString(R.string.field_format),
                                "Email"
                        )
                );
            }
        }

        return isValidated;

    }

    public boolean isPasswordValidated (EditText e, String password, String fieldName, boolean isRegister) {
        boolean isValidated = true;

        if (password.equals(Global.DEFAULT_STRING_VALUE)) {
            isValidated = !isValidated;
            e.setError(
                    String.format(
                            activity.getString(R.string.field_required),
                            fieldName
                    )
            );
        }

        if (isRegister) {
            if (!(password.length() >= 5 && password.length() <=30)) {
                if (isValidated) {
                    isValidated = !isValidated;
                    e.setError(
                            String.format(
                                    activity.getString(R.string.field_length),
                                    fieldName,
                                    5,
                                    30
                            )
                    );
                }
            }
        }

        return isValidated;

    }

    public void showQuestionDialog (DialogInterface.OnClickListener interfaceClickListener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("Yes", interfaceClickListener)
                .setNegativeButton("No", interfaceClickListener)
                .setCancelable(false)
        .show();

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

    public MLoadingDialog getDialog () {
        return dialog;
    }

    public boolean isExistedUser () {
        return isExistedUser;
    }

}
