package com.noscale.noscale_motocare.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.models.User;
import com.noscale.noscale_motocare.utils.Auth;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.MPreference;

/**
 * Created by kurniawanrizzki on 17/01/18.
 */

public class CommunicationReceiver extends BroadcastReceiver {

    private MainActivity activity;

    public CommunicationReceiver (MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String src = intent.getStringExtra(Global.SRC_DATA_EXTRA);
        String message = intent.getStringExtra(Global.RESPONSE_DATA_EXTRA);
        boolean status = intent.getBooleanExtra(Global.STATUS_DATA_EXTRA, false);

        activity.getFragmentController().getDialog().hide();

        switch (src) {
            case Global.GARAGE_TAG :
                onGarageListResponse(message, src, status);
                break;
            case Global.DETAIL_GARAGE_TAG :
                onDetailGarageResponse(message, src, status);
                break;
            case Global.PROFILE_TAG :
                onProfileEdited(message,src,status);
                break;
            case Global.BOOKING_TAG :
                onBooked(message, src, status);
                break;
            case Global.BOOKING_EXTRA_TAG :
                onBookingListResponse(message, src, status);
                break;
            default:
                login(message, src, status);
                break;
        }

    }

    private Response responseReader (String response, String tag) {

        JsonParser responseParser = new JsonParser();
        JsonObject responseObject = responseParser.parse(response).getAsJsonObject();

        String content = null;
        Response res = new Response();

        if (tag.equals(Global.LOGIN_TAG) || tag.equals(Global.REGISTER_TAG) || tag.equals(Global.PROFILE_TAG)) {
            content = "user";

            if (tag.equals(Global.LOGIN_TAG) || tag.equals(Global.REGISTER_TAG)) {
                res.setToken(responseObject.get("token").getAsString());
            }

        } else if (tag.equals(Global.GARAGE_TAG)) {
            content = "bengkels";
        } else if (tag.equals(Global.DETAIL_GARAGE_TAG)) {
            content = "bengkel";
        } else if (tag.equals(Global.BOOKING_TAG)) {
            content = "booked_service";
        } else if (tag.equals(Global.BOOKING_EXTRA_TAG)) {
            content = "booking";
        }

        res.setMsg(responseObject.get("msg").getAsString());
        res.setContent(responseObject.get(content).toString());

        return res;

    }

    private String errorReader (String message) {
        JsonParser errorParser = new JsonParser();
        JsonObject errorObject = errorParser.parse(message).getAsJsonObject();

        String errorMessage = activity.getString(R.string.password_not_match_text);

        if (null != errorObject.get("errors").getAsJsonObject().get("email")) {
            errorMessage = errorObject.get("errors").getAsJsonObject().get("email").getAsString();
        } else if (null != errorObject.get("msg")) {
            errorMessage = errorObject.get("msg").getAsString();
        }

        return errorMessage;
    }


    private void login (String message, String tag, boolean status) {

        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message, tag);

                if (response.getMsg().equals(activity.getString(R.string.user_created_response)) || response.getMsg().equals(activity.getString(R.string.user_signed_response))) {
                    activity.getFragmentController().getLoginFragment().getController().login(
                            response.getContent(),
                            response.getToken()
                    );
                    activity.getFragmentController().getMenuFragment().getController().getGarageFragment().getController().requestData();
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;

        }

        String errorResponse = message.equals(null)?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsJsonObject().get("msg").getAsString();

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();

    }

    private void onGarageListResponse (String message, String tag, boolean status) {

        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message,tag);

                if (response.getMsg().equals(activity.getString(R.string.bengkel_list_response))) {
                    activity.getFragmentController().getMenuFragment().getController().
                            getGarageFragment().getController().notifyDataSetChanged(response.getContent());
                    activity.getFragmentController().getMenuFragment().getController().getBridge().requestData();
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;
        }

        String errorResponse = null == message?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsString();

        if (errorResponse.equals(activity.getString(R.string.user_token_expired))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();

    }

    public void onDetailGarageResponse (String message, String tag, boolean status) {
        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message,tag);

                if (response.getMsg().equals(activity.getString(R.string.bengkel_info_response))) {
                    activity.getFragmentController().getMenuFragment().getController().getGarageFragment().getController().goToDetailPage(response.getContent());
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;
        }

        String errorResponse = null == message?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsString();

        if (errorResponse.equals(activity.getString(R.string.user_token_expired))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();
    }

    public void onProfileEdited (String message, String tag, boolean status) {
        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message,tag);

                if (response.getMsg().equals(activity.getString(R.string.user_udpated_response))) {
                    activity.getFragmentController().getMenuFragment().getController().getProfileFragment()
                            .getController().notifyDataSetChanged(response.getContent());
                } else if (response.getMsg().equals(activity.getString(R.string.user_pass_updated_response))){
                    String logoutMessage = response.getMsg()+"%1$s";
                    activity.getFragmentController().getLoginFragment().getController().logOut(
                            String.format(
                                    logoutMessage,
                                    activity.getString(R.string.relogin_text)
                            )
                    );
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;
        }

        String errorResponse = null == message?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsString();

        if (errorResponse.equals(activity.getString(R.string.user_token_expired))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();
    }

    private void onBooked (String message, String tag ,boolean status) {

        activity.getFragmentController().getMenuFragment().getController().getGarageFragment().getController()
                .getMDialog().dismiss();

        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message,tag);

                if (response.getMsg().equals(activity.getString(R.string.booked_text))) {
                    activity.getFragmentController().getMenuFragment().getController().getGarageFragment().getController().goToMySchedule(response.getMsg());
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;
        }

        String errorResponse = null == message?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsString();

        if (errorResponse.equals(activity.getString(R.string.user_token_expired))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();
    }

    private void onBookingListResponse (String message, String tag, boolean status) {
        if (status) {

            if (!message.contains("errors")) {
                Response response = responseReader(message,tag);

                if (response.getMsg().equals(activity.getString(R.string.book_response))) {
                    activity.getFragmentController().getMenuFragment().getController().getBridge().notifyDataSetChanged(response.getContent());
                } else {
                    Toast.makeText(activity,response.getMsg(),Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity,errorReader(message),Toast.LENGTH_LONG).show();
            }

            return;
        }

        String errorResponse = null == message?
                String.format(
                        activity.getString(R.string.failed_connect_to),Global.HOSTNAME
                ):new JsonParser().parse(message).getAsString();

        if (errorResponse.equals(activity.getString(R.string.user_token_expired))) {
            activity.getFragmentController().getLoginFragment().getController().logOut(errorResponse);
            return;
        }

        Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();
    }

    private class Response {
        private String msg;
        private String content;
        private String token;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
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
