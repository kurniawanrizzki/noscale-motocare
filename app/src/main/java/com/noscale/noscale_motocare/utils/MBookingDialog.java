package com.noscale.noscale_motocare.utils;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.adapters.DaysAdapter;
import com.noscale.noscale_motocare.adapters.ServicesAdapter;
import com.noscale.noscale_motocare.models.Garage;
import com.noscale.noscale_motocare.models.Service;

import java.util.HashMap;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class MBookingDialog {

    private MainActivity activity;
    private Dialog dialog;
    private Spinner servicesSpinner;
    private Spinner dateSpinner;
    private RadioGroup sessionRadio;
    private RadioButton selectedSession;
    private ImageView mClose;
    private ImageView mBooking;

    private Garage current;
    private ServicesAdapter servicesAdapter;
    private DaysAdapter daysAdapter;

    private HashMap<String,String> requestParams;

    public MBookingDialog (MainActivity activity) {
        this.activity = activity;

        servicesAdapter = new ServicesAdapter(activity);
        daysAdapter = new DaysAdapter(activity);
        requestParams = new HashMap<>();

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.booking_dialog_layout);
        dialog.setCanceledOnTouchOutside(false);
        initLayout();
        initEvent();
    }

    private void initLayout () {
        dateSpinner = (Spinner) dialog.findViewById(R.id.date_form_field);
        servicesSpinner = (Spinner) dialog.findViewById(R.id.services_form_field);
        sessionRadio = (RadioGroup) dialog.findViewById(R.id.radio_time_form);
        mClose = (ImageView) dialog.findViewById(R.id.close_btn);
        mBooking = (ImageView) dialog.findViewById(R.id.save_btn);
        dateSpinner.setAdapter(daysAdapter);
        servicesSpinner.setAdapter(servicesAdapter);
    }

    private void initEvent () {
        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestParams.put("layanan_id", String.valueOf(servicesAdapter.getItemId(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestParams.put("hari_id", String.valueOf(daysAdapter.getItemId(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragmentController().getDialog().show();
                onRequestBooking();
            }
        });
    }

    public void show () {
        daysAdapter.setDays(current.getDays());
        servicesAdapter.setServiceList(current.getServiceList());
        servicesAdapter.notifyDataSetChanged();
        daysAdapter.notifyDataSetChanged();
        dialog.show();
    }

    public void setCurrent (Garage current) {
        this.current = current;
    }

    public void dismiss () {
        requestParams.clear();
        dialog.dismiss();
    }

    private void onRequestBooking () {
        int sessionSelected = 1;
        int selectedId=sessionRadio.getCheckedRadioButtonId();
        selectedSession =(RadioButton)dialog.findViewById(selectedId);

        if (selectedSession.getText().toString().equals(activity.getString(R.string.date_second_session))) {
            sessionSelected = 2;
        }

        requestParams.put("bengkel_id", String.valueOf(current.getId()));
        requestParams.put("session_id", String.valueOf(sessionSelected));
        requestParams.put("token", Auth.getInstance(activity).getUser().getToken());

        RequestBuilder.getInstance(activity).build(
                Global.BOOKING_TAG,
                Global.BOOKING_SERVICE_API,
                Request.Method.POST,
                requestParams
        );

    }



}
