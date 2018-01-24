package com.noscale.noscale_motocare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.models.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class ServicesAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<Service> serviceList;

    public ServicesAdapter (Context context) {
        this.context = context;
        serviceList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return serviceList.get(position).getId();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LinearLayout bg = new LinearLayout(context);
        bg.setBackgroundResource(R.drawable.bg_spinner_list);
        bg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView txt = new TextView(context);
        Service item = serviceList.get(position);

        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(item.getServiceName());
        txt.setTextColor(ContextCompat.getColor(context, R.color.colorMBlack));

        bg.addView(txt);

        return bg;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {

        LinearLayout bg = new LinearLayout(context);
        bg.setBackgroundResource(R.drawable.bg_spinner_list);
        bg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView txt = new TextView(context);

        Service item = serviceList.get(i);

        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(item.getServiceName());
        txt.setTextColor(ContextCompat.getColor(context, R.color.colorMBlack));
        bg.addView(txt);

        return bg;
    }

    public void setServiceList (List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public List<Service> getServiceList () {
        return serviceList;
    }

}
