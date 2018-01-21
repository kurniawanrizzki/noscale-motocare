package com.noscale.noscale_motocare.controllers;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.LoginFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.RegisterFragment;
import com.noscale.noscale_motocare.models.User;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.DummySingleton;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MPreference;

import java.util.HashMap;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class LoginController {

    private LoginFragment fragment;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private Button mCreateAccount;

    private MainActivity activity;

    public LoginController (LoginFragment fragment) {
        this.fragment = fragment;
        activity = (MainActivity) fragment.getActivity();
        initLayout();
        initEvent();
    }

    private void initLayout () {
        mUsername = (EditText) fragment.getView().findViewById(R.id.username_field);
        mPassword = (EditText) fragment.getView().findViewById(R.id.password_field);
        mLogin = (Button) fragment.getView().findViewById(R.id.login_button);
        mCreateAccount = (Button) fragment.getView().findViewById(R.id.create_acc_button);

        if (Global.IS_DEMO_VERSION) {
            User user = DummySingleton.getInstance().getDummyUser();
            mUsername.setText(user.getUsername());
            mPassword.setText(user.getPassword());
        }

    }

    private void initEvent () {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = null != mUsername.getText()?mUsername.getText().toString(): Global.DEFAULT_STRING_VALUE;
                String password = null != mPassword.getText()?mPassword.getText().toString():Global.DEFAULT_STRING_VALUE;

                if (validate(username, password)) {
                    activity.getFragmentController().getDialog().show();

                    if (Global.IS_DEMO_VERSION) {
                        User dummyUser = DummySingleton.getInstance().getDummyUser();
                        if (dummyUser.getUsername().equals(username) && dummyUser.getPassword().equals(password)) {

                            String userJson = Auth.getInstance(activity).setUser(dummyUser);
                            MPreference.getInstance(activity).putStringToMPreference(userJson, Global.EXISTED_USER_PREF);

                            activity.getFragmentController().getDialog().hide();
                            goToHomePage();

                        }

                        return;
                    }

                    Auth.getInstance(activity).authenticate(username, password);
                }

            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            RegisterFragment registerFragment = activity.getFragmentController().getRegisterFragment();
            activity.getFragmentController().showFragment(registerFragment);

            }
        });
    }

    private boolean validate (String username, String password) {
        boolean isUsernameValidated = activity.getFragmentController().isEmailValidated(mUsername,username);
        boolean isPasswordValidated = activity.getFragmentController().isPasswordValidated(mPassword, password, "Password", false);
        return isUsernameValidated && isPasswordValidated;
    }

    public void login (String content, String token) {

        User createdUser = Auth.getInstance(activity).getUserContentFromResponse(
                content, token
        );

        String userJson = Auth.getInstance(activity).setUser(createdUser);

        MPreference.getInstance(activity).putStringToMPreference(userJson, Global.EXISTED_USER_PREF);
        activity.getFragmentController().getLoginFragment().getController().goToHomePage();
        Toast.makeText(activity,String.format(
                activity.getString(R.string.user_welcome)
                , createdUser.getUsername()
        ),Toast.LENGTH_LONG).show();


    }

    public void goToHomePage () {
        MenuFragment menuFragment = activity.getFragmentController().getMenuFragment();
        activity.getFragmentController().showFragment(menuFragment);
    }

    public void logOut (String message) {
        MPreference.getInstance(activity).putStringToMPreference(null, Global.EXISTED_USER_PREF);

        mUsername.setText(Global.DEFAULT_STRING_VALUE);
        mPassword.setText(Global.DEFAULT_STRING_VALUE);

        activity.getFragmentController().showFragment(fragment);
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
