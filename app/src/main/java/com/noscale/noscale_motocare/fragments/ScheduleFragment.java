package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class ScheduleFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        ((MainActivity) getActivity()).getFragmentController().getMenuFragment().getBookingController().initScheduleLayout(this);
        return view;
    }

    public View getView () {
        return view;
    }

}
