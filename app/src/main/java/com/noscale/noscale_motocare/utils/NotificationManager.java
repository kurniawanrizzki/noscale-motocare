package com.noscale.noscale_motocare.utils;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.PopupMenu;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.models.Schedule;

import java.util.List;

/**
 * Created by kurniawanrrizki on 27/12/17.
 */

public class NotificationManager {

    private MainActivity activity;
    private View notificationMenu;
    private PopupMenu notificationPopup;

    private List<Schedule> schedules;

    public NotificationManager(MainActivity activity) {
        this.activity = activity;
        initData();
        initLayout();
    }

    private void initData() {

        if (Global.IS_DEMO_VERSION) {
            schedules = DummySingleton.getInstance().getSchedule();
        }

    }

    public void initPopup() {
        notificationMenu = activity.findViewById(R.id.notification);
        notificationPopup = new PopupMenu(activity, notificationMenu);
        for (Schedule schedule:schedules) {
            notificationPopup.getMenu().add(schedule.getGarageName());
        }
    }

    public void initLayout () {
        if (schedules.size() > 0) {
            activity.getMenu().getItem(0).setIcon(
                    ContextCompat.getDrawable(activity,R.drawable.ic_notifications_active)
            );
        }
    }

    public PopupMenu getNotificationPopup () {
        return notificationPopup;
    }

}
