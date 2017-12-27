package com.noscale.noscale_motocare.controllers;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.fragments.RegisterFragment;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class RegisterController {

    private RegisterFragment fragment;

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mCPassword;
    private Button mRegisterButton;

    public RegisterController (RegisterFragment fragment) {
        this.fragment = fragment;
        initLayout();
        initEvent();
    }

    private void initLayout () {
        mUsername = (EditText) fragment.getView().findViewById(R.id.username_field);
        mEmail = (EditText) fragment.getView().findViewById(R.id.email_field);
        mPassword = (EditText) fragment.getView().findViewById(R.id.password_field);
        mCPassword = (EditText) fragment.getView().findViewById(R.id.cpassword_field);
        mRegisterButton = (Button) fragment.getView().findViewById(R.id.submit_reg_button);
    }

    private void initEvent () {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
