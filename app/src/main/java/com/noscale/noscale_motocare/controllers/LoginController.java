package com.noscale.noscale_motocare.controllers;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.LoginFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.RegisterFragment;
import com.noscale.noscale_motocare.models.User;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.DummySingleton;
import com.noscale.noscale_motocare.utils.Global;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class LoginController {

    private LoginFragment fragment;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private Button mCreateAccount;

    public LoginController (LoginFragment fragment) {
        this.fragment = fragment;
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

                if (Auth.getInstance(fragment.getContext()).authenticate(username, password)) {
                    MenuFragment menuFragment = ((MainActivity) fragment.getActivity()).getFragmentController().getMenuFragment();
                    ((MainActivity) menuFragment.getActivity()).getFragmentController().showFragment(menuFragment);
                }
            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = ((MainActivity) fragment.getActivity()).getFragmentController().getRegisterFragment();
                ((MainActivity) registerFragment.getActivity()).getFragmentController().showFragment(registerFragment);
            }
        });
    }

}
