package com.noscale.noscale_motocare.activities;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.adapters.LeftDrawerAdapter;
import com.noscale.noscale_motocare.broadcasts.CommunicationReceiver;
import com.noscale.noscale_motocare.controllers.FragmentController;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.NotificationManager;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private FragmentController fragmentController;
    private Menu menu;
    private CommunicationReceiver receiver;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LeftDrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        initLayout();
        initEvent();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initLayout () {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_drawer);
        mDrawerList = (ListView) findViewById(R.id.acitivity_menu);
        mDrawerAdapter = new LeftDrawerAdapter(this);
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        fragmentController = new FragmentController(this);

    }

    private void initEvent() {
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private static final int PROFILE = 0;
            private static final int LOGOUT = 1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == LOGOUT) {
                    fragmentController.showAlertDialog(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        mDrawerLayout.closeDrawer(Gravity.START);
                                        fragmentController.getLoginFragment().getController().logOut("Log Out");
                                        return;
                                    }
                                    dialog.dismiss();
                                }
                            }, getString(R.string.logout_message), true
                    );
                } else if (position == PROFILE) {
                    fragmentController.getMenuFragment().getMenuController().goToProfilePage();
                }
            }
        });

        mDrawerLayout.addDrawerListener(mDrawerToggle);

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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        fragmentController.onItemSelectedOption(item.getItemId());
        return super.onOptionsItemSelected(item);
    }



    public Menu getMenu () {
        return menu;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null == receiver) {
            receiver = new CommunicationReceiver(this);
            registerReceiver(receiver, new IntentFilter(Global.COMMUNICATION_INTENT));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != receiver) {
            unregisterReceiver(receiver);
            receiver = null;
        }

    }

    public DrawerLayout getMDrawerLayout () {
        return mDrawerLayout;
    }

    public NotificationManager getNotificationManager () {
        return notificationManager;
    }

    public FragmentController getFragmentController () {
        return fragmentController;
    }

    public ActionBarDrawerToggle getMDrawerToggle () {
        return mDrawerToggle;
    }

}
