package com.noscale.noscale_motocare.utils;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class Global {

    public static final String HOSTNAME = "http://mss-febui.com/motocare/";

    public static final boolean IS_DEMO_VERSION = false;
    public static final String DEFAULT_STRING_VALUE = "";
    public static final String DUMMY_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjYsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjAuMTA5OjgwMDAvYXBpL3YxL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE1MTYzOTk1OTgsImV4cCI6MTUxNjQwMzE5OCwibmJmIjoxNTE2Mzk5NTk4LCJqdGkiOiI2cDV2VmV2RWxKVkd3T2pGIn0.byq4-8RWIjUGvvkjRCjUJqqDAT9RhykHxN4vPTH3ro0";

    public static final int DEFAULT_INT_VALUE = 0;
    public static final long SPLASH_TIMEOUT = 1000;

    public static final String LOGIN_TAG = "LOGIN";
    public static final String REGISTER_TAG = "REGISTER";
    public static final String MENU_TAG = "MENU";
    public static final String GARAGE_TAG = "GARAGE";
    public static final String SCHEDULE_TAG = "SCHEDULE";
    public static final String HISTORY_TAG = "HISTORY";
    public static final String DETAIL_GARAGE_TAG = "DETAIL GARAGE";
    public static final String PROFILE_TAG = "PROFILE";
    public static final String BOOKING_TAG = "BOOKING";
    public static final String BOOKING_EXTRA_TAG = "BOOKING_EXTRA_TAG";

    public static final String EXISTED_USER_PREF = "EXISTED_USER_PREF";

    public static final int FULL_TO_ORDER = 0;
    public static final int AVAILABLE_TO_ORDER = 1;

    public static final int SERVICE_TYPE_FULL = 0;
    public static final int SERVICE_TYPE_PARTIALLY = 1;

    public static final int SCHEDULE_NOT_ACTIVE = 0;
    public static final int SCHEDULE_IS_ACTIVE = 1;

    public static final String COMMUNICATION_INTENT = "com.noscale.motocare.communication";
    public static final String RESPONSE_DATA_EXTRA = "RESPONSE_DATA_EXTRA";
    public static final String STATUS_DATA_EXTRA = "STATUS_DATA_EXTRA";
    public static final String SRC_DATA_EXTRA = "SRC_DATA_EXTRA";

    public static final String LOGIN_API = HOSTNAME+"api/v1/user/signin";
    public static final String REGISTER_API = HOSTNAME+"api/v1/user/register";
    public static final String PROFILE_FORM_API = HOSTNAME+"api/v1/user/edit";
    public static final String PROFILE_PASSWORD_API = HOSTNAME+"api/v1/user/change-pass";
    public static final String GARAGE_LIST_API = HOSTNAME+"api/v1/bengkel?token=%1$s";
    public static final String GARAGE_SERVICE_API = HOSTNAME+"api/v1/bengkel/%1$d?token=%2$s";
    public static final String BOOKING_SERVICE_API =HOSTNAME+"api/v1/booking";
    public static final String BOOKING_EXTRA_SERVICE_API =HOSTNAME+"api/v1/booking?token=%1$s";

}
