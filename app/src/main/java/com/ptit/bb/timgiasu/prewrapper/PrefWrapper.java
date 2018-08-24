package com.ptit.bb.timgiasu.prewrapper;

import android.content.Context;
import android.content.SharedPreferences;

import com.gemvietnam.utils.StringUtils;
import com.google.gson.Gson;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

public class PrefWrapper {

    private static final String MY_PREFERENCES = "Pref";

    private static final String KEY_USER = "user";

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Save User as json
     */
    public static void saveUser(Context context, UserDTO user) {
        String userJson = new Gson().toJson(user);
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }

    /**
     * Get User from saved json
     */
    public static synchronized UserDTO getUser(Context context) {
        String userJson = getPreference(context).getString(KEY_USER, null);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }

        return new Gson().fromJson(userJson, UserDTO.class);
    }
}
