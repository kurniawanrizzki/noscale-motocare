package com.noscale.noscale_motocare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.BookingController;
import com.noscale.noscale_motocare.controllers.MenuController;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MenuFragment extends Fragment {

    private View view;
    private MenuController menuController;
    private BookingController bookingController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        menuController = new MenuController(this);
        bookingController = new BookingController(this);
        return view;
    }

    public View getView () {
        return view;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public BookingController getBookingController () {
        return bookingController;
    }
}
