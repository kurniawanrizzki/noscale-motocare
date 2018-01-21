package com.noscale.noscale_motocare.utils.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noscale.noscale_motocare.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by kurniawanrizzki on 17/01/18.
 */

public class ResponseListener implements Response.Listener<String>, Response.ErrorListener {

    private Context context;
    private String src;

    public ResponseListener (Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

        String errorResponse = null;

        try {

            if (null != volleyError.networkResponse.data) {
                errorResponse = new String( volleyError.networkResponse.data, Charset.forName("UTF8"));
                Log.d("Motocare Error Response",errorResponse+"\n"+volleyError.getNetworkTimeMs());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        sendBroadcast(errorResponse, false);

    }

    @Override
    public void onResponse(String response) {
        sendBroadcast(response, true);
        Log.d("Motocare Response", response);
    }

    private void sendBroadcast (String message, boolean status) {

        Intent communicationIntent = new Intent(Global.COMMUNICATION_INTENT);
        communicationIntent.putExtra(Global.RESPONSE_DATA_EXTRA, message);
        communicationIntent.putExtra(Global.STATUS_DATA_EXTRA, status);
        communicationIntent.putExtra(Global.SRC_DATA_EXTRA, src);
        context.sendBroadcast(communicationIntent);

    }

    public void setSrc(String src) {
        this.src = src;
    }

}
