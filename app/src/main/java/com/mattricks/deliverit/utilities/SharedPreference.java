package com.mattricks.deliverit.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Distributor;
import com.mattricks.deliverit.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mahasagar on 16/4/16.
 */
public class SharedPreference {


    public SharedPreference() {
        super();
    }


    public void storeUser(Context context, JSONObject user) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        if(user != null && user.toString() != ""){
            editor.putString(Constants.USER_PREF, user.toString());
            editor.putBoolean(Constants.IS_USER_PREF, true);
        }else{
            editor.putString(Constants.USER_PREF, null);
            editor.putBoolean(Constants.IS_USER_PREF, false);
        }
        editor.commit();
    }
    public String getUser(Context context) {
        String User = loadUser(context);
        if (User != null && User != "") {
            return User;
        }
        return null;
    }

    public String getUserId(Context context) throws JSONException {
        String User = loadUser(context);
        JSONObject JsonResult = new JSONObject(User);
        Log.d("JsonResult : ",JsonResult.getString("email"));
        //JSONArray JsonDistributorArray = new JSONArray(object.getString("distributors"));
          //  JSONObject JsonDistributorObject = JsonDistributorArray.getJSONObject(j);
            if (JsonResult != null && JsonResult.toString() != "") {
            return JsonResult.getString("_id");
        }
        return null;
    }

    public String getUserName(Context context) throws JSONException {
        String User = loadUser(context);
        JSONObject JsonResult = new JSONObject(User);
        Log.d("JsonResult : ",JsonResult.getString("email"));
        //JSONArray JsonDistributorArray = new JSONArray(object.getString("distributors"));
        //  JSONObject JsonDistributorObject = JsonDistributorArray.getJSONObject(j);
        if (JsonResult != null && JsonResult.toString() != "") {
            return JsonResult.getString("name");
        }
        return null;
    }






    public boolean isLogin(Context context) {
        SharedPreferences settings;
        boolean isLogin=false;
        settings = context.getSharedPreferences(Constants.PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(Constants.IS_USER_PREF)) {
            isLogin = settings.getBoolean(Constants.IS_USER_PREF, false);
            System.out.println("isLogin : " + isLogin);
        } else
            return false;

        return isLogin;
    }

    public String loadUser(Context context) {
        SharedPreferences settings;
        String jsonUser = "";
        settings = context.getSharedPreferences(Constants.PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(Constants.USER_PREF)) {
            jsonUser = settings.getString(Constants.USER_PREF, null);
            System.out.println("jsonFavorites" + jsonUser);
        } else
            return null;
        return jsonUser;
    }
    public void addUser(Context context, JSONObject userDetails) {
        storeUser(context, userDetails);
    }
    public void removeUser(Context context) {
        storeUser(context, null);
    }


}
