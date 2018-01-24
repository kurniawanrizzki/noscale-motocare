package com.noscale.noscale_motocare.utils;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.adapters.NotificationAdapter;
import com.noscale.noscale_motocare.models.Booking;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by kurniawanrrizki on 27/12/17.
 */

public class NotificationManager {

    private MainActivity activity;
    private NotificationAdapter adapter;
    private View notificationMenu;
    private PopupWindow notificationPopup;

    List<Booking> firedBookingList;

    public NotificationManager(MainActivity activity) {
        this.activity = activity;
        firedBookingList = new ArrayList<>();
    }

    public void initPopup() {
        notificationMenu = activity.findViewById(R.id.notification);
        notificationPopup = new PopupWindow(activity);

        adapter = new NotificationAdapter(activity);
        View layout = activity.getLayoutInflater().inflate(R.layout.notification_layout,null);
        ListView notificationList = (ListView) layout.findViewById(R.id.notification_listview);
        notificationList.setAdapter(adapter);

        notificationPopup.setContentView(layout);
        notificationPopup.setOutsideTouchable(false);
    }

    public void fire (List<Booking> bookings) {

        if (null == notificationPopup) {
            initPopup();
        }

        if (adapter.getNotificationScheduleList().size() > 0) {
            show();
        }

        populateNotification(bookings);
        if (firedBookingList.size() > 0) {
            initLayout();
            initData();
        }
    }

    private void initLayout () {
        activity.getMenu().getItem(0).setIcon(
                ContextCompat.getDrawable(activity,R.drawable.ic_notifications_active)
        );
    }

    private void initData () {
        for (Booking booking:firedBookingList) {
            adapter.getNotificationScheduleList().add(booking);
        }
        adapter.notifyDataSetChanged();
    }

    private void populateNotification (List<Booking> bookings) {
        for (Booking schedule:bookings) {
            if (isNotificationFired(schedule)) {
                firedBookingList.add(schedule);
            }
        }
    }

    private boolean isNotificationFired (Booking item) {

        int hour = 8;

        if (!item.getSession().equals(activity.getString(R.string.date_first_session))) {
            hour = 13;
        }

        if (isToday(item.getDate()) && !isCancelable(item.getDate(),hour)) {
            return true;
        }

        return false;
    }

    public boolean isToday (String item) {

        int day = getDay(item);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        if (day != calendar.get(Calendar.DAY_OF_WEEK)) {
            return false;
        }

        return true;
    }

    public boolean isCancelable (String day, int sessionHour) {

        int dayOfWeek = getDay(day);

        Calendar time = Calendar.getInstance(Locale.getDefault());
        time.add(Calendar.HOUR, 1);

        Calendar sessionTime = Calendar.getInstance(Locale.getDefault());
        sessionTime.add(Calendar.DATE, getNextDay(dayOfWeek));
        sessionTime.set(Calendar.HOUR_OF_DAY, sessionHour);
        sessionTime.set(Calendar.MINUTE, 0);

        long deviation = sessionTime.getTimeInMillis() - time.getTimeInMillis();

        if (deviation > 0) {
            return true;
        }

        return false;

    }

    private int getDay (String item) {

        int day = Global.DAY[0];

        if (item.equals("Minggu")) {
            day = Global.DAY[0];
        } else if (item.equals("Senin")) {
            day = Global.DAY[1];
        } else if (item.equals("Selasa")) {
            day = Global.DAY[2];
        } else if (item.equals("Rabu")) {
            day = Global.DAY[3];
        } else if (item.equals("Kamis")) {
            day = Global.DAY[4];
        } else if (item.equals("Jumat")) {
            day = Global.DAY[5];
        } else if (item.equals("Sabtu")) {
            day = Global.DAY[6];
        }

        return day;
    }

    private int getNextDay (int day) {

        int today = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_WEEK);
        int begin = today;
        int different=0;

        while (begin != day) {
            for (int index = begin;index<Global.DAY.length;index++) {
                if (index != day) {
                    different++;
                    if (index == 6) {
                        begin = 0;
                        break;
                    }
                    continue;
                }
                begin = day;
                break;
            }
        }

        return different;

    }

    public void show () {
        notificationPopup.showAsDropDown(notificationMenu);
    }

    public NotificationAdapter getAdapter () {
        return adapter;
    }

    public void clear () {

        if (null != notificationPopup) {
            firedBookingList.clear();
            adapter.getNotificationScheduleList().clear();
            adapter.notifyDataSetChanged();
            activity.getMenu().getItem(0).setIcon(
                    ContextCompat.getDrawable(activity,R.drawable.ic_notifications_none)
            );
        }

    }

}
