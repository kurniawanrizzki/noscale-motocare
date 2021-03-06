package com.noscale.noscale_motocare.controllers;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.volley.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.adapters.BookingFrameAdapter;
import com.noscale.noscale_motocare.fragments.HistoryFragment;
import com.noscale.noscale_motocare.fragments.MenuFragment;
import com.noscale.noscale_motocare.fragments.ScheduleFragment;
import com.noscale.noscale_motocare.models.Booking;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.RequestBuilder;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class BookingController {

    private MenuFragment fragment;
    private BookingFrameAdapter scheduleAdapter;
    private BookingFrameAdapter historyAdapter;

    private RecyclerView scheduleView;
    private RecyclerView historyView;


    public BookingController (MenuFragment fragment) {
        this.fragment = fragment;
        initData();
    }

    private void initData () {
        MainActivity activity = (MainActivity) fragment.getActivity();
        scheduleAdapter = new BookingFrameAdapter(activity);
        historyAdapter = new BookingFrameAdapter(activity);
    }

    public void initScheduleLayout (ScheduleFragment fragment) {

        scheduleView = (RecyclerView) fragment.getView().findViewById(R.id.schedule_frame);
        LinearLayoutManager llManager = new LinearLayoutManager(fragment.getContext());

        scheduleView.setHasFixedSize(true);
        scheduleView.setLayoutManager(llManager);

        scheduleView.setAdapter(scheduleAdapter);

    }

    public void initHistoryLayout (HistoryFragment fragment) {
        historyView = (RecyclerView) fragment.getView().findViewById(R.id.history_frame);
        LinearLayoutManager llManager = new LinearLayoutManager(fragment.getContext());

        historyView.setHasFixedSize(true);
        historyView.setLayoutManager(llManager);

        historyView.setAdapter(historyAdapter);
    }

    public void requestData () {

        MainActivity activity = ((MainActivity) fragment.getActivity());

        RequestBuilder.getInstance(activity).build(
                String.format(
                        Global.BOOKING_EXTRA_SERVICE_API,
                        Auth.getInstance(activity).getUser().getToken()
                ),
                Request.Method.GET,
                null
        );

    }

    public void cancelBooking (int id) {
        MainActivity activity = ((MainActivity) fragment.getActivity());

        RequestBuilder.getInstance(fragment.getActivity()).build(
                String.format(
                        Global.BOOKING_CANCEL_API,
                        id,
                        Auth.getInstance(activity).getUser().getToken()
                ),
                Request.Method.DELETE,
                null
        );
    }

    public void notifyDataSetChanged (String message) {

        scheduleAdapter.getBookingList().clear();
        historyAdapter.getBookingList().clear();
        JsonArray bookingListArray = new JsonParser().parse(message).getAsJsonArray();

        for (JsonElement bookingElement:bookingListArray) {

            JsonObject bookedJson = bookingElement.getAsJsonObject();
            Booking item = getBooking(bookedJson);

            if (!item.getStatus().equals("done") && !item.getStatus().equals("canceled")) {
                scheduleAdapter.getBookingList().add(
                        item
                );
            } else {
                historyAdapter.getBookingList().add(
                        item
                );
            }

        }

        scheduleAdapter.notifyDataSetChanged();
        historyAdapter.notifyDataSetChanged();

        ((MainActivity) fragment.getActivity()).getNotificationManager()
                .fire(scheduleAdapter.getBookingList());

    }

    public void notifyGarageHasDeleted() {

        ((MainActivity) fragment.getActivity()).getFragmentController().showAlertDialog(
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {

                            ((MainActivity) fragment.getActivity()).getFragmentController().getDialog().show();
                            requestData();

                        }
                    }
                }
                ,"You have been delete your service.", false);

    }

    public Booking getBooking (JsonObject itemJson) {
        Booking item = new Booking();
        item.setId(
                itemJson.get("id").getAsInt()
        );
        item.setStatus(
                itemJson.get("status").getAsString()
        );
        item.setBookingCode(
                itemJson.get("kode_booking").getAsString()
        );
        item.setDate(
                itemJson.get("hari").getAsJsonObject().get("hari").getAsString()
        );
        item.setPrice(
                itemJson.get("harga_layanan").getAsFloat()
        );
        item.setServices(
                itemJson.get("layanan_obj").getAsJsonObject().get("nama_layanan").getAsString()
        );
        item.setSession(
                itemJson.get("session_id").getAsString().equals(fragment.getString(R.string.date_first_session))?
                        fragment.getString(R.string.date_first_session):fragment.getString(R.string.date_second_session)
        );

        item.setGarage(
                itemJson.get("data_bengkel").getAsJsonObject().get("nama_bengkel").getAsString()
        );

        return item;
    }

    public BookingFrameAdapter getScheduleAdapter () {
        return scheduleAdapter;
    }

    public BookingFrameAdapter getHistoryAdapter () {
        return historyAdapter;
    }


}
