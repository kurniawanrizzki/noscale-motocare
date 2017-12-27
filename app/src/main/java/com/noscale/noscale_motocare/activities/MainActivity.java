package com.noscale.noscale_motocare.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.FragmentController;
import com.noscale.noscale_motocare.utils.NotificationManager;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private FragmentController fragmentController;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        fragmentController = new FragmentController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification, menu);
        notificationManager = new NotificationManager(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentController.onItemSelectedOption(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public Menu getMenu () {
        return menu;
    }

    public NotificationManager getNotificationManager () {
        return notificationManager;
    }

    public FragmentController getFragmentController () {
        return fragmentController;
    }

}
