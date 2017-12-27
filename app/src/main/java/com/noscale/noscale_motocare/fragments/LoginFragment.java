package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.LoginController;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class LoginFragment extends Fragment {

    private View view;
    private LoginController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        controller = new LoginController(this);
        return view;
    }

    public View getView () {
        return view;
    }

    public LoginController getController () {
        return controller;
    }

}
