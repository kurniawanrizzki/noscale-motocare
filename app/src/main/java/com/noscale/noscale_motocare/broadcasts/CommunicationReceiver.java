package com.noscale.noscale_motocare.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.utils.Global;

/**
 * Created by kurniawanrizzki on 17/01/18.
 */

public class CommunicationReceiver extends BroadcastReceiver {

    private MainActivity activity;
    private static final int LOGIN_RES = 0;
    private static final int REGISTER_RES = 1;
    private static final int PROFILEUPDATED_RES = 2;
    private static final int PROFILEPASSWORDUPDATED_RES = 3;
    private static final int GARAGE_LIST_RES = 4;
    private static final int GARAGE_DETAIL_RES = 5;
    private static final int BOOKING_LIST_RES = 6;
    private static final int BOOKING_RES = 7;
    private static final int CANCELBOOKING_RES = 8;

    public CommunicationReceiver (MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(Global.RESPONSE_DATA_EXTRA);
        boolean status = intent.getBooleanExtra(Global.STATUS_DATA_EXTRA, false);

        activity.getFragmentController().getDialog().hide();

        if ((null != message) && !message.contains("token_expired") && !message.contains("token_invalid")) {

            if (!message.contains("errors")) {

                Response response = responseReader(message);

                if ((null != response) && status) {

                    switch (response.getResCode()) {
                        case PROFILEUPDATED_RES:
                            activity.getFragmentController().getMenuFragment().getMenuController().getProfileFragment()
                                    .getController().notifyDataSetChanged(response.getContent());
                            break;
                        case PROFILEPASSWORDUPDATED_RES:
                            String logoutMessage = response.getResCode()+"%1$s";
                            activity.getFragmentController().getLoginFragment().getController().logOut(
                                    String.format(
                                            logoutMessage,
                                            activity.getString(R.string.relogin_text)
                                    )
                            );
                            break;
                        case GARAGE_LIST_RES:
                            onGarageListResponse(response);
                            break;
                        case GARAGE_DETAIL_RES :
                            onDetailGarageResponse(response);
                            break;
                        case BOOKING_LIST_RES:
                            onBookingListResponse(response);
                            break;
                        case BOOKING_RES :
                            onBooked();
                            break;
                        case CANCELBOOKING_RES :
                            onDeleted();
                            break;
                        default:
                            login(response);
                            break;
                    }

                }


            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;

        }

        showErrorResponse(message);

    }

    private Response responseReader (String response) {

        JsonParser responseParser = new JsonParser();
        JsonObject responseObject = responseParser.parse(response).getAsJsonObject();

        int resCode = LOGIN_RES;
        String content;
        Response res = new Response();
        String msg = responseObject.get("msg").getAsString();

        if (msg.equals(activity.getString(R.string.user_signed_response))) {

            content = "user";
            res.setToken(responseObject.get("token").getAsString());

        } else if (msg.equals(activity.getString(R.string.user_created_response))) {

            content = "user";
            resCode = REGISTER_RES;
            res.setToken(responseObject.get("token").getAsString());

        } else if (msg.equals(activity.getString(R.string.user_udpated_response))) {

            content = "user";
            resCode = PROFILEUPDATED_RES;

        } else if (msg.equals(activity.getString(R.string.user_pass_updated_response))) {

            content = "user";
            resCode = PROFILEPASSWORDUPDATED_RES;

        } else if (msg.equals(activity.getString(R.string.bengkel_list_response))) {

            content = "bengkels";
            resCode = GARAGE_LIST_RES;

        } else if (msg.equals(activity.getString(R.string.bengkel_info_response))) {

            content = "bengkel";
            resCode = GARAGE_DETAIL_RES;

        } else if (msg.equals(activity.getString(R.string.booked_response))) {

            content = "booked_service";
            resCode = BOOKING_RES;

        } else if (msg.equals(activity.getString(R.string.booked_list_response))) {

            content = "booking";
            resCode = BOOKING_LIST_RES;

        } else if (msg.equals("User canceled the service")) {
            content = "bengkel";
            resCode = CANCELBOOKING_RES;
        } else {
            Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
            return null;
        }

        res.setResCode(resCode);
        res.setContent(responseObject.get(content).toString());

        return res;

    }

    private String errorReader (String message) {
        JsonParser errorParser = new JsonParser();
        JsonObject errorObject = errorParser.parse(message).getAsJsonObject();

        String errorMessage = activity.getString(R.string.password_not_match_text);

        if (null != errorObject.get("errors").getAsJsonObject().get("email")) {
            errorMessage = errorObject.get("errors").getAsJsonObject().get("email").getAsString();
        } else if (null != errorObject.get("resCode")) {
            errorMessage = errorObject.get("resCode").getAsString();
        } else if (null != errorObject.get("msg")) {
            errorMessage = errorObject.get("msg").getAsString();
        }

        return errorMessage;
    }

    private void showErrorResponse (String message) {

        String errorResponse;
        try {

            errorResponse = (null == message)?String.format(
                    activity.getString(R.string.failed_connect_to),Global.HOSTNAME
            ):new JsonParser().parse(message).getAsJsonObject().get("msg").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            errorResponse = new JsonParser().parse(message).getAsString();
        }

        if (errorResponse.equals(activity.getString(R.string.user_token_expired)) || errorResponse.equals(activity.getString(R.string.user_tokent_invalid))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();
    }


    private void login (Response response) {

        activity.getFragmentController().getLoginFragment().getController().login(
                response.getContent(),
                response.getToken()
        );

    }

    private void onGarageListResponse (Response response) {

        activity.getFragmentController().getMenuFragment().getMenuController().
                getGarageFragment().getController().notifyDataSetChanged(response.getContent());

    }

    public void onDetailGarageResponse (Response response) {
        activity.getFragmentController().getMenuFragment().getMenuController().getGarageFragment().getController().goToDetailPage(response.getContent());
    }

    private void onBooked () {

        activity.getFragmentController().getMenuFragment().getMenuController()
                .getGarageFragment().getController().notifyGarageHasBooked();

    }

    private void onDeleted () {
        activity.getFragmentController().getMenuFragment().getBookingController().notifyGarageHasDeleted();
    }

    private void onBookingListResponse (Response response) {
        activity.getFragmentController().getMenuFragment()
                .getBookingController().notifyDataSetChanged(response.getContent());

    }

    private class Response {
        private int resCode;
        private String content;
        private String token;

        public int getResCode() {
            return resCode;
        }

        public void setResCode(int resCode) {
            this.resCode = resCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
