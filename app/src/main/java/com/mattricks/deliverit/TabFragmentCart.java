package com.mattricks.deliverit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.adapters.ProductAdapter;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahasagar on 10/11/16.
 */
public class TabFragmentCart extends Fragment {

    BottomBar bottomBar;
    RequestQueue requestQueue;
    SharedPreference sharedPreference;
    String UserId="";
    public TabFragmentCart(){

        sharedPreference = new SharedPreference();
    }

    public static TabFragmentCart newInstance(String text, BottomBar bottomBar) {

        TabFragmentCart sampleFragment = new TabFragmentCart();
        sampleFragment.bottomBar = bottomBar;
        //sampleFragment.setArguments(args);
        return sampleFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.tabfragmentcart, container, false);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        try {
            Log.d("UserId b #: ", UserId);
            UserId = sharedPreference.getUserId(getActivity());
            Log.d("# UserId #: ", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getCartDetails(rootView);
        return rootView;
    }



    private void getCartDetails(final View view) {

        final String URL = Constants.APP_URL + Constants.URL_GETCARTDETAILS;
        Log.d("URL #: ", URL);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response #: ", response);
                        try {
                            JSONArray JsonResult = new JSONArray(response);
                            Product product = null;
                            for (int i = 0; i < JsonResult.length(); i++) {
                                JSONObject object = JsonResult.getJSONObject(i);
                                Log.d("object #: ", object.toString());

                            }
                        } catch (Exception e) {
                            Log.d("e #: ", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.e("Error.Response #", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("businessId",UserId);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

}