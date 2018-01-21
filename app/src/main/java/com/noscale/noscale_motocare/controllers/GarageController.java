package com.noscale.noscale_motocare.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.adapters.GarageFrameAdapter;
import com.noscale.noscale_motocare.adapters.MenuAdapter;
import com.noscale.noscale_motocare.fragments.DetailGarageFragment;
import com.noscale.noscale_motocare.fragments.GarageFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.models.Day;
import com.noscale.noscale_motocare.models.Garage;
import com.noscale.noscale_motocare.models.Service;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MBookingDialog;
import com.noscale.noscale_motocare.utils.RequestBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurniawanrizzki on 20/01/18.
 */

public class GarageController {

    private MainActivity activity;
    private GarageFragment fragment;
    private DetailGarageFragment detailGarageFragment;

    private RecyclerView garageFrame;
    private GarageFrameAdapter adapter;

    private TextView mTitle;
    private TextView mAddress;
    private TextView mDescription;
    private NetworkImageView mImage;
    private LinearLayout bookingButton;
    private MBookingDialog mDialog;

    private boolean isRequestHasBeenExecuted;

    public GarageController (GarageFragment fragment) {
        this.activity = (MainActivity) fragment.getActivity();
        this.fragment = fragment;
        initData();
        initLayout();
    }

    private void initData () {
        adapter = new GarageFrameAdapter(this);
        detailGarageFragment = new DetailGarageFragment();
        mDialog = new MBookingDialog(activity);

        if (activity.getFragmentController().isExistedUser() && !isRequestHasBeenExecuted) {
            requestData();
            isRequestHasBeenExecuted = true;
        }

    }

    private void initLayout () {

        if (!activity.getFragmentController().isFragmentHasBeenExisted(Global.DETAIL_GARAGE_TAG)) {
            activity.getFragmentController().putFragmentIntoLayout(detailGarageFragment, Global.DETAIL_GARAGE_TAG);
        }

        garageFrame = (RecyclerView) fragment.getView().findViewById(R.id.garage_frame);
        LinearLayoutManager llManager = new LinearLayoutManager(activity);

        garageFrame.setHasFixedSize(true);
        garageFrame.setLayoutManager(llManager);

        garageFrame.setAdapter(adapter);

    }

    public void initSubLayout (View view) {
        mTitle = (TextView) view.findViewById(R.id.title);
        mAddress = (TextView) view.findViewById(R.id.address);
        mDescription = (TextView) view.findViewById(R.id.content_description);
        mImage = (NetworkImageView) view.findViewById(R.id.image);
        bookingButton = (LinearLayout) view.findViewById(R.id.booking_button);

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });

    }

    public void requestData () {

        activity.getFragmentController().getDialog().show();

        RequestBuilder.getInstance(fragment.getActivity()).build(
                Global.GARAGE_TAG,
                String.format(
                        Global.GARAGE_LIST_API,
                        Auth.getInstance(activity).getUser().getToken()
                ),
                Request.Method.GET,
                null
        );

    }

    public void notifyDataSetChanged (String message) {

        adapter.setGarageList(new ArrayList<Garage>());
        JsonArray garageListJson = new JsonParser().parse(message).getAsJsonArray();

        for (JsonElement garageElement:garageListJson) {

            JsonObject garage = garageElement.getAsJsonObject();
            adapter.getGarageList().add(
                    getGarage(garage)
            );

        }

        adapter.notifyDataSetChanged();

    }

    private Garage getGarage (JsonObject garage) {

        Garage item = new Garage();

        item.setId(
                garage.get("id").getAsInt()
        );

        item.setName(
                garage.get("nama_bengkel").getAsString()
        );

        item.setAddress(
                garage.get("lokasi_bengkel").getAsString()
        );

        item.setDescription(
                garage.get("info_bengkel").getAsString()
        );

        item.setLat(
                !garage.get("latitude").getAsString().equals(Global.DEFAULT_STRING_VALUE)?
                        garage.get("latitude").getAsDouble():
                        0
        );

        item.setLng(
                !garage.get("longitude").getAsString().equals(Global.DEFAULT_STRING_VALUE)?
                        garage.get("longitude").getAsDouble():
                        0
        );

        item.setSessionOne(
                !garage.get("session_one").getAsString().equals(Global.DEFAULT_STRING_VALUE)?
                        garage.get("session_one").getAsInt():
                        0
        );

        item.setSessionTwo(
                !garage.get("session_two").getAsString().equals(Global.DEFAULT_STRING_VALUE)?
                        garage.get("session_two").getAsInt():
                        0
        );

        item.setPhoto(
                garage.get("foto_bengkel").getAsString()
        );

        if (null != garage.get("layanan")) {
            JsonArray servicesArray = garage.get("layanan").getAsJsonArray();
            item.setServiceList(getServices(servicesArray));
        }

        if (null != garage.get("hari")) {
            JsonArray daysArray = garage.get("hari").getAsJsonArray();
            item.setDays(getDays(daysArray));
        }

        return item;

    }

    private List<Service> getServices (JsonArray servicesArray) {
        List<Service> services = new ArrayList<>();

        for (JsonElement element:servicesArray) {
            JsonObject service = element.getAsJsonObject();

            Service item = new Service();

            item.setId(
                    service.get("id").getAsInt()
            );

            item.setServiceName(
                    service.get("nama_layanan").getAsString()
            );

            item.setServiceWeight(
                    service.get("bobot_layanan").getAsInt()
            );

            item.setServicePrice(
                    service.get("harga_layanan").getAsInt()
            );

            services.add(item);

        }

        return services;
    }

    private List<Day> getDays (JsonArray daysArray) {
        List<Day> days = new ArrayList<>();

        for (JsonElement element:daysArray) {
            JsonObject dayJson = element.getAsJsonObject();
            Day item = new Day();
            item.setId(dayJson.get("id").getAsInt());
            item.setDay(dayJson.get("hari").getAsString());
            days.add(item);
        }

        return days;
    }

    public void goToDetailPage (String message) {
        JsonObject garageJson = new JsonParser().parse(message).getAsJsonObject();
        Garage item = getGarage(garageJson);

        mDialog.setCurrent(item);

        mTitle.setText(item.getName());
        mAddress.setText(item.getAddress());
        mDescription.setText(item.getDescription());
        loadImage(item.getPhoto(), mImage);

        activity.getFragmentController().showFragment(detailGarageFragment);
        detailGarageFragment.showOnTheMap(
                item.getLat(),
                item.getLng(),
                item.getName()
        );
    }

    public void onItemRequestService (int garageId) {

        activity.getFragmentController().getDialog().show();
        RequestBuilder.getInstance(activity).build(
                Global.DETAIL_GARAGE_TAG,
                String.format(
                        Global.GARAGE_SERVICE_API,
                        garageId,
                        Auth.getInstance(activity).getUser().getToken()
                ),
                Request.Method.GET,
                null
        );

    }

    public void goToMySchedule (String message) {

        MenuFragment fragment = activity.getFragmentController().getMenuFragment();
        activity.getFragmentController().showFragment(fragment);
        fragment.getController().goToAnotherTab(MenuAdapter.SCHEDULE_PAGE);

        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

    }

    public void loadImage (String url, NetworkImageView v) {
        ImageLoader loader = RequestBuilder.getInstance(activity).loadImage();
        loader.get(url, ImageLoader.getImageListener(v,R.drawable.no_image, R.drawable.no_image));
        v.setImageUrl(url,loader);
    }

    public MBookingDialog getMDialog () {
        return mDialog;
    }

}
