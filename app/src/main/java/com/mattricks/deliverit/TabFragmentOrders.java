package com.mattricks.deliverit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.adapters.CartAdapter;
import com.mattricks.deliverit.adapters.OrderAdapter;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Cart;
import com.mattricks.deliverit.model.CartProduct;
import com.mattricks.deliverit.model.Order;
import com.mattricks.deliverit.utilities.DividerItemDecoration;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mahasagar on 10/11/16.
 */
public class TabFragmentOrders extends Fragment {


    RequestQueue requestQueue;
    SharedPreference sharedPreference;
    @Bind(R.id.listEmptyMsg)
    CardView listEmptyMsg;

    @Bind(R.id.recycler_view_orders)
    RecyclerView recyclerViewOrder;
    RecyclerView.LayoutManager mLayoutManager;
    private OrderAdapter mAdapter;
    private List<Order> orderList = new ArrayList<>();
    String UserName = null, UserId = null;

    public TabFragmentOrders() {

        sharedPreference = new SharedPreference();
    }

    public static TabFragmentOrders newInstance(String text, BottomBar bottomBar) {
        Bundle args = new Bundle();
        TabFragmentOrders sampleFragment = new TabFragmentOrders();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabfragmentorder, container, false);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewOrder.setLayoutManager(mLayoutManager);
        recyclerViewOrder.setAdapter(mAdapter);

        try {
            UserId = sharedPreference.getUserId(getActivity());
            UserName = sharedPreference.getUserName(getActivity());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("$$$$$$$$ b #: ", e.toString());
        }

        mAdapter = new OrderAdapter(orderList, getActivity(), UserId, TabFragmentOrders.this);
        getOrderDetails(rootView);
        return rootView;
    }


    private void getOrderDetails(final View view) {

        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        final String URL = Constants.APP_URL + Constants.URL_GETORDERS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        orderList.clear();
                        try {
                            JSONArray jsonArrayOrders = new JSONArray(response);

                            for (int i = 0; i < jsonArrayOrders.length(); i++) {
                                JSONObject jsonOneOrder = jsonArrayOrders.getJSONObject(i);
                                Order order = new Order();

                                JSONObject businessInfo = jsonOneOrder.getJSONObject("businessInfo");
                                JSONObject toInfo = businessInfo.getJSONObject("to");
                                order.setDistributorName(toInfo.getString("name"));
                                order.setOrderDate(jsonOneOrder.getString("createdDate"));
                                order.setOrderTotal(jsonOneOrder.getString("grandTotal"));
                                order.setStatus(jsonOneOrder.getString("orderStatus"));
                                order.setOrderId(jsonOneOrder.getString("orderId"));
                                orderList.add(order);

                            }
                        } catch (Exception e) {
                            Log.d("e #: ", e.getMessage());
                        }
                        try {
                            if(orderList.isEmpty()){
                                recyclerViewOrder.setVisibility(View.GONE);
                                listEmptyMsg.setVisibility(View.VISIBLE);
                            }else {
                                recyclerViewOrder.setVisibility(View.VISIBLE);
                                listEmptyMsg.setVisibility(View.GONE);
                            }
                            mAdapter = new OrderAdapter(orderList, getActivity(), UserId, TabFragmentOrders.this);
                            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                            recyclerViewOrder.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewOrder.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                            recyclerViewOrder.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                            itemAnimator.setAddDuration(1000);
                            itemAnimator.setRemoveDuration(1000);
                            recyclerViewOrder.setItemAnimator(itemAnimator);
                            recyclerViewOrder.setLayoutManager(mLayoutManager);

                            recyclerViewOrder.setNestedScrollingEnabled(false);
                            recyclerViewOrder.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        } catch (NullPointerException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response #", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("businessId", UserId.trim());
                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}