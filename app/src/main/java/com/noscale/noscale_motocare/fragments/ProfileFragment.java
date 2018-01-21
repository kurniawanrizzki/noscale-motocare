package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.ProfileController;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class ProfileFragment extends Fragment {

    private View view;
    private ProfileController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        controller = new ProfileController(this);
        return view;
    }

    public View getView () {
        return view;
    }

    public ProfileController getController () {
        return controller;
    }

}
