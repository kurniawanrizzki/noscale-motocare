package com.noscale.noscale_motocare.controllers;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.fragments.ProfileFragment;
import com.noscale.noscale_motocare.models.User;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MPreference;
import com.noscale.noscale_motocare.utils.RequestBuilder;

import java.util.HashMap;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class ProfileController {

    private ProfileFragment fragment;
    private TextView mEmailText;
    private TextView mUsernameText;
    private TextView mPhoneText;

    private EditText mUsername;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;

    private RelativeLayout editFormLayout;
    private RelativeLayout approveFormLayout;
    private RelativeLayout editPasswordLayout;
    private RelativeLayout approvePasswordLayout;
    private RelativeLayout confirmPasswordLayout;

    private ImageView mSaveForm;
    private ImageView mCancelForm;
    private ImageView mSavePassword;
    private ImageView mCancelPassword;

    public ProfileController (ProfileFragment fragment) {
        this.fragment = fragment;
        initLayout();
        initEvent();
    }

    private void initLayout () {
        mEmailText = (TextView) fragment.getView().findViewById(R.id.email_profile);
        mUsernameText = (TextView) fragment.getView().findViewById(R.id.username_profile);
        mPhoneText = (TextView) fragment.getView().findViewById(R.id.phone_profile);

        mUsername = (EditText) fragment.getView().findViewById(R.id.username_field);
        mPhone = (EditText) fragment.getView().findViewById(R.id.phone_field);
        mPassword = (EditText) fragment.getView().findViewById(R.id.password_field);
        mConfirmPassword = (EditText) fragment.getView().findViewById(R.id.cpassword_field);

        editFormLayout = (RelativeLayout) fragment.getView().findViewById(R.id.form_data_edit_fixed);
        approveFormLayout = (RelativeLayout) fragment.getView().findViewById(R.id.form_approve_edit_wrapper);
        editPasswordLayout = (RelativeLayout) fragment.getView().findViewById(R.id.form_password_edit_fixed);
        approvePasswordLayout = (RelativeLayout) fragment.getView().findViewById(R.id.form_approve_password_wrapper);
        confirmPasswordLayout = (RelativeLayout) fragment.getView().findViewById(R.id.cpassword_wrapper);

        mSaveForm = (ImageView) fragment.getView().findViewById(R.id.form_approve_edit);
        mCancelForm = (ImageView) fragment.getView().findViewById(R.id.form_cancel_edit);
        mSavePassword = (ImageView) fragment.getView().findViewById(R.id.form_approve_password_edit);
        mCancelPassword = (ImageView) fragment.getView().findViewById(R.id.form_cancel_password_edit);
    }

    private void initEvent () {
        editFormLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User profile = Auth.getInstance(fragment.getContext()).getUser();
                showFormHiddenLayout(approveFormLayout, v);
                showHideFormComponent(View.GONE, View.VISIBLE);
                setFormComponentValue(
                        profile.getUsername(),
                        profile.getPhone()
                );
            }
        });

        editPasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFormHiddenLayout(approvePasswordLayout, v);
                showHidePasswordCompmonent(View.VISIBLE);
            }
        });

        mSaveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isFormValidated()) {
                    ((MainActivity) fragment.getActivity()).getFragmentController().showQuestionDialog(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        HashMap<String, String> formParams = new HashMap<>();
                                        formParams.put("name",mUsername.getText().toString());
                                        formParams.put("no_telepon",mPhone.getText().toString());
                                        formParams.put("token", Auth.getInstance(v.getContext()).getUser().getToken());
                                        requestToStore(Global.PROFILE_FORM_API,formParams);
                                        return;
                                    }
                                    dialog.dismiss();
                                }
                            }, fragment.getActivity().getString(R.string.change_form_question)
                    );
                }
            }
        });

        mCancelForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFormComponentValue(null,null);
                hideFormDisplayedLayout(approveFormLayout, editFormLayout);
                showHideFormComponent(View.VISIBLE, View.GONE);
            }
        });

        mSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isPasswordValidated()) {
                    ((MainActivity) fragment.getActivity()).getFragmentController().showQuestionDialog(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                HashMap<String, String> passwordParams = new HashMap<>();
                                passwordParams.put("password",mPassword.getText().toString());
                                passwordParams.put("token", Auth.getInstance(v.getContext()).getUser().getToken());
                                requestToStore(Global.PROFILE_PASSWORD_API,passwordParams);
                                return;
                            }
                            dialog.dismiss();
                        }
                    }, fragment.getActivity().getString(R.string.change_form_question));
                }
            }
        });

        mCancelPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFormDisplayedLayout(approvePasswordLayout, editPasswordLayout);
                showHidePasswordCompmonent(View.GONE);
            }
        });

    }

    private boolean isFormValidated() {
        MainActivity activity = ((MainActivity) fragment.getActivity());

        String username = mUsername.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                Global.DEFAULT_STRING_VALUE:mUsername.getText().toString();
        String phone = mPhone.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                Global.DEFAULT_STRING_VALUE:mPhone.getText().toString();

        boolean isUsernameValidated = activity.getFragmentController().getRegisterFragment().getController().isUserNameValidate(mUsername, username);
        boolean isPhoneValidated = activity.getFragmentController().getRegisterFragment().getController().isPhoneValidated(mPhone, phone);

        return isPhoneValidated && isUsernameValidated;
    }

    private boolean isPasswordValidated () {
        MainActivity activity = ((MainActivity) fragment.getActivity());

        String password = mPassword.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                Global.DEFAULT_STRING_VALUE:mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString().equals(Global.DEFAULT_STRING_VALUE)?
                Global.DEFAULT_STRING_VALUE:mConfirmPassword.getText().toString();

        boolean isPasswordValidated = activity.getFragmentController().isPasswordValidated(mPassword, password, "Password", true);
        boolean isConfirmValidated = activity.getFragmentController().isPasswordValidated(mConfirmPassword, confirmPassword, "Confirm Password", true);
        boolean isPasswordMatched = activity.getFragmentController().getRegisterFragment().getController().isConfirmPasswordMatched(mConfirmPassword, password, confirmPassword);

        return  isPasswordValidated && isConfirmValidated && isPasswordMatched;

    }

    private void showFormHiddenLayout (View approveLayout, View editLayout) {
        approveLayout.setVisibility(View.VISIBLE);
        editLayout.setVisibility(View.GONE);
    }

    private void hideFormDisplayedLayout (View approveLayout, View editLayout) {
        approveLayout.setVisibility(View.GONE);
        editLayout.setVisibility(View.VISIBLE);
    }

    private void showHideFormComponent (int textVisibility, int fieldVisibility) {
        mUsernameText.setVisibility(textVisibility);
        mPhoneText.setVisibility(textVisibility);
        mUsername.setVisibility(fieldVisibility);
        mPhone.setVisibility(fieldVisibility);
    }

    private void showHidePasswordCompmonent (int fieldVisibility) {
        mPassword.setVisibility(fieldVisibility);
        confirmPasswordLayout.setVisibility(fieldVisibility);
    }

    public void setProfileText() {
        User profile = Auth.getInstance(fragment.getContext()).getUser();
        mEmailText.setText(profile.getEmail());
        mUsernameText.setText(profile.getUsername());
        mPhoneText.setText(profile.getPhone());
    }

    public void resetForm (String username, String phone) {
        setFormComponentValue(username, phone);

        if (approveFormLayout.getVisibility() == View.VISIBLE) {
            hideFormDisplayedLayout(approveFormLayout, editFormLayout);
            showHideFormComponent(View.VISIBLE, View.GONE);
        }

        if (approvePasswordLayout.getVisibility() == View.VISIBLE) {
            showHidePasswordCompmonent(View.GONE);
            hideFormDisplayedLayout(approvePasswordLayout, editPasswordLayout);
        }

    }

    private void setFormComponentValue(String username, String phone) {
        mUsername.setText(username);
        mPhone.setText(phone);
    }

    private void requestToStore (String url, HashMap<String, String> params) {
        ((MainActivity) fragment.getActivity()).getFragmentController().getDialog().show();
        RequestBuilder.getInstance(fragment.getContext()).build(
                Global.PROFILE_TAG,
                url,
                Request.Method.POST,
                params
        );
    }

    public void notifyDataSetChanged(String response) {

        String token = Auth.getInstance(fragment.getContext()).getUser().getToken();
        User changedUserInfo = Auth.getInstance(fragment.getContext()).getUserContentFromResponse(response,token);
        String changedUserJson = Auth.getInstance(fragment.getContext()).setUser(changedUserInfo);

        MPreference.getInstance(fragment.getContext()).putStringToMPreference(changedUserJson, Global.EXISTED_USER_PREF);
        resetForm(changedUserInfo.getUsername(), changedUserInfo.getPhone());
        setProfileText();

        Toast.makeText(fragment.getContext(),fragment.getContext().getString(R.string.user_information_changed),Toast.LENGTH_LONG).show();

    }

}
