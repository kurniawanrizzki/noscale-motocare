package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.RegisterController;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class RegisterFragment extends Fragment {

    private View view;
    private RegisterController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        controller = new RegisterController(this);
        return view;
    }

    public View getView () {
        return view;
    }

    public RegisterController getController () {
        return controller;
    }
}
