package com.noscale.noscale_motocare.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.noscale.noscale_motocare.models.User;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class Auth {

    private static Context ctx;
    private static Auth instance;

    public static Auth getInstance (Context context) {
        if (null == instance) {
            instance = new Auth();
            ctx = context;
        }

        return instance;
    }

    public void authenticate (String username, String password) {

        HashMap<String, String> loginParams = new HashMap<>();
        loginParams.put("email",username);
        loginParams.put("password",password);
        RequestBuilder.getInstance(ctx).build(Global.LOGIN_TAG, Global.LOGIN_API, Request.Method.POST, loginParams);

    }

    public void register (String username, String password, String email, String phone) {

        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("name", username);
        registerParams.put("password", password);
        registerParams.put("email", email);
        registerParams.put("no_telepon", phone);
        RequestBuilder.getInstance(ctx).build(Global.REGISTER_TAG, Global.REGISTER_API, Request.Method.POST, registerParams);
    }

    public User getUserContentFromResponse (String content, String token) {
        JsonParser parser = new JsonParser();
        JsonObject userJson = parser.parse(content).getAsJsonObject();

        return new User(
                userJson.get("id").getAsInt(),
                userJson.get("name").getAsString(),
                null,
                userJson.get("no_telepon").getAsString(),
                userJson.get("email").getAsString(),
                token
        );
    }

    public String setUser (User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public User getUser () {

        Gson gson = new Gson();
        String userJson = MPreference.getInstance(ctx).getStringFromMPreference(Global.EXISTED_USER_PREF, Global.DEFAULT_STRING_VALUE);
        return gson.fromJson(userJson, User.class);
    }

}
