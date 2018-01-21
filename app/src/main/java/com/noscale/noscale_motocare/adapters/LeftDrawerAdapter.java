package com.noscale.noscale_motocare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noscale.noscale_motocare.R;

import java.util.ArrayList;

/**
 * Created by kurniawanrizzki on 20/01/18.
 */

public class LeftDrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavigationItem> navigationItems;

    public LeftDrawerAdapter (Context context) {
        this.context = context;
        navigationItems = new ArrayList<>();
        populateMenuItem();
    }

    @Override
    public int getCount() {
        return navigationItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private void populateMenuItem () {
        NavigationItem item1 = new NavigationItem();
        item1.setNavIcon(R.drawable.ic_account_circle);
        item1.setNavTitle("Profile");
        item1.setNavSubtitle("View user profile");

        navigationItems.add(item1);

        NavigationItem item2 = new NavigationItem();

        item2.setNavIcon(R.drawable.ic_exit_to_app);
        item2.setNavTitle("Log Out");
        item2.setNavSubtitle("Exit");

        navigationItems.add(item2);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_menu_layout, null);
        }
        else {
            view = convertView;
        }

        TextView title = (TextView) view.findViewById(R.id.menu_title);
        TextView subTitle = (TextView) view.findViewById(R.id.menu_subtitle);
        ImageView img = (ImageView) view.findViewById(R.id.menu_icon);

        NavigationItem item = navigationItems.get(position);
        title.setText(item.getNavTitle());
        subTitle.setText(item.getNavSubtitle());
        img.setImageDrawable(ContextCompat.getDrawable(context, item.getNavIcon()));

        return view;
    }

    private class NavigationItem {
        private int navIcon;
        private String navTitle;
        private String navSubtitle;

        public int getNavIcon() {
            return navIcon;
        }

        public void setNavIcon(int navIcon) {
            this.navIcon = navIcon;
        }

        public String getNavTitle() {
            return navTitle;
        }

        public void setNavTitle(String navTitle) {
            this.navTitle = navTitle;
        }

        public String getNavSubtitle() {
            return navSubtitle;
        }

        public void setNavSubtitle(String navSubtitle) {
            this.navSubtitle = navSubtitle;
        }
    }

}
