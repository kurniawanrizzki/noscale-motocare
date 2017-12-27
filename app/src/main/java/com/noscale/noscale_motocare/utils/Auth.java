package com.noscale.noscale_motocare.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.noscale.noscale_motocare.models.User;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class Auth {

    private static Context ctx;
    private static Auth instance;
    private User user;

    public static Auth getInstance (Context context) {
        if (null == instance) {
            instance = new Auth();
            ctx = context;
        }

        return instance;
    }

    public boolean authenticate (String username, String password) {

        User user;

        if (Global.IS_DEMO_VERSION) {
            user = DummySingleton.getInstance().getDummyUser();
        }

        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            stored(user);
            return true;
        }

        return false;
    }

    public boolean register (User user) {

        User existedUser;

        if (Global.IS_DEMO_VERSION) {
            existedUser = DummySingleton.getInstance().getDummyUser();

            if (!existedUser.getUsername().equals(user.getUsername())) {
                stored(user);
                return true;
            }

            return false;

        }

        if (null == existedUser) {
            stored(existedUser);
            return true;
        }

        return false;
    }

    private void stored (User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        MPreference.getInstance(ctx).putStringToMPreference(userJson, Global.EXTRA_USER);
    }

    public User getUser () {

        if (null == user) {
            Gson gson = new Gson();
            String userJson = MPreference.getInstance(ctx).getStringFromMPreference(Global.EXTRA_USER, Global.DEFAULT_STRING_VALUE);
            user = gson.fromJson(userJson, User.class);
        }

        return user;

    }

}
