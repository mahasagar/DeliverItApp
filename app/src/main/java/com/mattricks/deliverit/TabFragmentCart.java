package com.mattricks.deliverit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.adapters.CartAdapter;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Cart;
import com.mattricks.deliverit.model.CartProduct;
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
public class TabFragmentCart extends Fragment {

    BottomBar bottomBar;
    RequestQueue requestQueue;
    SharedPreference sharedPreference;
    @Bind(R.id.recycler_view_cart) RecyclerView recyclerViewCart;
    @Bind(R.id.listEmptyMsg) CardView listEmptyMsg;

    RecyclerView.LayoutManager mLayoutManager;
    private CartAdapter mAdapter;
    private List<Cart> cartList = new ArrayList<>();
    String UserName = null, UserId = null;
    private String UserMobile;

    public TabFragmentCart() {
        sharedPreference = new SharedPreference();
    }

    public static TabFragmentCart newInstance(String text, BottomBar bottomBar) {
        TabFragmentCart sampleFragment = new TabFragmentCart();
        sampleFragment.bottomBar = bottomBar;
        return sampleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabfragmentcart, container, false);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();

        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewCart.setLayoutManager(mLayoutManager);

        recyclerViewCart.setAdapter(mAdapter);
        try {
            UserId = sharedPreference.getUserId(getActivity());
            UserName = sharedPreference.getUserName(getActivity());

            UserMobile = sharedPreference.getUserMobileNumber(getActivity());

          } catch (Exception e) {
            e.printStackTrace();
         }

        mAdapter = new CartAdapter(cartList, getActivity(), UserId, UserName, UserMobile, TabFragmentCart.this);
        getCartDetails(rootView);
        return rootView;
    }


    public void getCartDetails(final View view) {

        mAdapter = new CartAdapter(cartList, getActivity(), UserId, UserName, UserMobile, TabFragmentCart.this);
        final String URL = Constants.APP_URL + Constants.URL_GETCARTDETAILS;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cartList.clear();
                        try {
                            JSONObject JsonResult = new JSONObject(response);
                            String jsonArray = JsonResult.getString("result");
                            JSONArray json_items = new JSONArray(jsonArray);

                            for (int i = 0; i < json_items.length(); i++) {
                                JSONObject object = json_items.getJSONObject(i);
                                Cart cart = new Cart();
                                cart.DistributorName = object.getString("distributorName");
                                cart.TotalPrice = object.getString("total");
                                cart.DistributorId = object.getString("distributorId");
                                // object.getString("items");
                                JSONArray json_items_products = new JSONArray(object.getString("items"));
                                ArrayList<CartProduct> cartProductList = new ArrayList<>();
                                for (int j = 0; j < json_items_products.length(); j++) {
                                    JSONObject objectOneProduct = json_items_products.getJSONObject(j);
                                    CartProduct newItem = new CartProduct();

                                    newItem.setName(objectOneProduct.getString("name"));
                                    newItem.setBrand(objectOneProduct.getString("brand"));
                                    newItem.setForm(objectOneProduct.getString("form"));
                                    newItem.setPackType(objectOneProduct.getString("packType"));
                                    newItem.setPackSize(objectOneProduct.getString("packSize"));
                                    newItem.setQuantity(objectOneProduct.getString("orderQty"));
                                    newItem.setDistributorPrice(objectOneProduct.getString("unitPrice"));
                                    newItem.setMRP(objectOneProduct.getString("MRP"));
                                    newItem.setProductId(objectOneProduct.getString("productId"));
                                    newItem.setDistributorId(objectOneProduct.getString("distributorId"));
                                    newItem.setDistributorName(objectOneProduct.getString("distributorName"));
                                    newItem.setTotalPricePerProduct(objectOneProduct.getString("total"));
                                    cartProductList.add(newItem);
                                }
                                cart.Items = cartProductList;
                                cartList.add(cart);

                            }
                        } catch (Exception e) {
                            Log.d("e #: ", e.getMessage());
                        }
                        try {
                            if(cartList.isEmpty()){
                                recyclerViewCart.setVisibility(View.GONE);
                                listEmptyMsg.setVisibility(View.VISIBLE);
                            }else {
                                recyclerViewCart.setVisibility(View.VISIBLE);
                                listEmptyMsg.setVisibility(View.GONE);
                            }
                            mAdapter = new CartAdapter(cartList, getActivity(), UserId, UserName, UserMobile, TabFragmentCart.this);
                            mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                            recyclerViewCart.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewCart.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                            recyclerViewCart.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                            itemAnimator.setAddDuration(1000);
                            itemAnimator.setRemoveDuration(1000);
                            recyclerViewCart.setItemAnimator(itemAnimator);
                            recyclerViewCart.setLayoutManager(mLayoutManager);

                            recyclerViewCart.setNestedScrollingEnabled(false);
                            recyclerViewCart.setAdapter(mAdapter);
                            restoreRecycleView();
                            mAdapter.notifyDataSetChanged();
                         } catch (NullPointerException e) {
                            Log.e("TagFramgmentCart",e.toString());
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
                Map<String, String> params = new HashMap<>();
                params.put("businessId", UserId);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void restoreRecycleView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        try {
            recyclerViewCart.setLayoutManager(mLayoutManager);
            recyclerViewCart.setItemAnimator(new DefaultItemAnimator());
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            recyclerViewCart.setItemAnimator(itemAnimator);
        } catch (NullPointerException e) {
            Log.e("TagFramgmentCart",e.toString());
        }
    }


}