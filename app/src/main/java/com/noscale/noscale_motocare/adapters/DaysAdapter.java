package com.noscale.noscale_motocare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.models.Day;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class DaysAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context;
    private List<Day> days;

    public DaysAdapter (Context context) {
        this.context = context;
        days = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return days.get(position).getId();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);

        String day = days.get(position).getDay();

        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(day);
        txt.setTextColor(ContextCompat.getColor(context, R.color.colorMBlack));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);

        String day = days.get(i).getDay();

        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(day);
        txt.setTextColor(ContextCompat.getColor(context, R.color.colorMBlack));
        return txt;
    }

    public void setDays (List<Day> days) {
        this.days = days;
    }
}
