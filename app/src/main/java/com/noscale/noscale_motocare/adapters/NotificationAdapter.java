package com.noscale.noscale_motocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.models.Booking;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurniawanrizzki on 24/01/18.
 */

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private List<Booking> notificationScheduleList;

    public NotificationAdapter (Context context) {
        this.context = context;
        notificationScheduleList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return notificationScheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationScheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationScheduleList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_notification, null);
        }
        else {
            view = convertView;
        }

        Booking item = notificationScheduleList.get(position);

        TextView notificationTitle = (TextView) view.findViewById(R.id.notification_title);
        TextView notificationSession = (TextView) view.findViewById(R.id.notification_session);

        notificationTitle.setText(item.getGarage()+" - "+item.getServices());
        notificationSession.setText(item.getSession());

        return view;
    }

    public List<Booking> getNotificationScheduleList () {
        return notificationScheduleList;
    }
}
