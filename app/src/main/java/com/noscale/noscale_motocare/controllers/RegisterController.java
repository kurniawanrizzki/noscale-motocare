package com.noscale.noscale_motocare.controllers;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.RegisterFragment;
import com.noscale.noscale_motocare.models.User;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.DummySingleton;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MPreference;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class RegisterController {

    private MainActivity activity;
    private RegisterFragment fragment;

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mCPassword;
    private Button mRegisterButton;

    public RegisterController (RegisterFragment fragment) {
        this.fragment = fragment;
        activity = (MainActivity) fragment.getActivity();
        initLayout();
        initEvent();
    }

    private void initLayout () {
        mUsername = (EditText) fragment.getView().findViewById(R.id.username_field);
        mEmail = (EditText) fragment.getView().findViewById(R.id.email_field);
        mPhone = (EditText) fragment.getView().findViewById(R.id.phone_field);
        mPassword = (EditText) fragment.getView().findViewById(R.id.password_field);
        mCPassword = (EditText) fragment.getView().findViewById(R.id.cpassword_field);
        mRegisterButton = (Button) fragment.getView().findViewById(R.id.submit_reg_button);
    }

    private void initEvent () {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                        Global.DEFAULT_STRING_VALUE:mUsername.getText().toString();
                String password = mPassword.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                        Global.DEFAULT_STRING_VALUE:mPassword.getText().toString();
                String confirmPassword = mCPassword.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                        Global.DEFAULT_STRING_VALUE:mCPassword.getText().toString();
                String phone = mPhone.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                        Global.DEFAULT_STRING_VALUE:mPhone.getText().toString();
                String email = mEmail.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                        Global.DEFAULT_STRING_VALUE:mEmail.getText().toString();

                if (validate(
                        username,
                        email,
                        phone,
                        password,
                        confirmPassword
                )) {

                    activity.getFragmentController().getDialog().show();

                    if (Global.IS_DEMO_VERSION) {
                        User dummyExistedUser = DummySingleton.getInstance().getDummyUser();

                        if (!dummyExistedUser.getUsername().equals(mUsername.getText().toString())) {
                            User registerUser = new User(
                                    Global.DEFAULT_INT_VALUE,
                                    username,
                                    password,
                                    phone,
                                    email,
                                    Global.DUMMY_TOKEN
                            );

                            String userJson = Auth.getInstance(activity).setUser(registerUser);

                            MPreference.getInstance(activity).putStringToMPreference(userJson, Global.EXISTED_USER_PREF);

                            activity.getFragmentController().getDialog().hide();
                            activity.getFragmentController().getLoginFragment().getController().goToHomePage();

                        }

                        return;

                    }

                    Auth.getInstance(activity).register(
                            username,
                            password,
                            email,
                            phone
                    );

                }
            }
        });
    }

    public boolean validate (String username, String email, String phone, String password, String confirmPassword) {

        boolean isUsernameValidated = isUserNameValidate(mUsername, username);
        boolean isEmailValidated = activity.getFragmentController().isEmailValidated(mEmail,email);
        boolean isPhoneValidated = isPhoneValidated(mPhone, phone);
        boolean isPasswordValidated = activity.getFragmentController().isPasswordValidated(mPassword, password, "Password", true);
        boolean isCPasswordValidated = activity.getFragmentController().isPasswordValidated(mCPassword, confirmPassword, "Confirm Password", true);
        boolean isCPasswordMathced = isConfirmPasswordMatched(mCPassword, password, confirmPassword);

        return  isUsernameValidated && isEmailValidated && isPhoneValidated &&
                isPasswordValidated && isCPasswordValidated  && isCPasswordMathced;
    }

    public boolean isUserNameValidate (EditText mUsername, String username) {
        boolean isValidated = true;

        if (username.equals(Global.DEFAULT_STRING_VALUE)) {
            isValidated = !isValidated;
            mUsername.setError(
                    String.format(
                            activity.getString(R.string.field_required),
                            "Username"
                    )
            );
        }

        if (!(username.length() > 6 && username.length() <=30)) {
            if (isValidated) {
                isValidated = !isValidated;
                mUsername.setError(
                        String.format(
                                activity.getString(R.string.field_length),
                                "Username",
                                6,
                                30
                        )
                );
            }
        }

        return isValidated;

    }



    public boolean isPhoneValidated (EditText mPhone, String phone) {

        boolean isValidated = true;

        if (phone.equals(Global.DEFAULT_STRING_VALUE)) {
            isValidated = !isValidated;
            mPhone.setError(
                    String.format(
                            activity.getString(R.string.field_required),
                            "Phone"
                    )
            );
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            if (isValidated) {
                isValidated = !isValidated;
                mPhone.setError(
                        String.format(
                                activity.getString(R.string.field_format),
                                "Phone"
                        )
                );
            }
        }

        if (!(phone.length() > 7 && phone.length() <=15)) {
            if (isValidated) {
                isValidated = !isValidated;
                mPhone.setError(
                        String.format(
                                activity.getString(R.string.field_length),
                                "Phone",
                                7,
                                15
                        )
                );
            }
        }

        return isValidated;

    }

    public boolean isConfirmPasswordMatched (EditText e, String password, String confirmPassword) {
        boolean isValidated = true;

        if (!password.equals(confirmPassword)) {
            isValidated = !isValidated;
            e.setError(activity.getString(R.string.password_matched));
        }

        return isValidated;
    }

}
