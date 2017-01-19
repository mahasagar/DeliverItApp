package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.TabFragmentCart;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Cart;
import com.mattricks.deliverit.model.CartProduct;
import com.mattricks.deliverit.model.Distributor;
import com.mattricks.deliverit.utilities.DividerItemDecoration;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahasagar on 17/1/17.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Cart> cartList;
    private List<CartProduct> cartProductsList;
    Activity thisActivity;
    RequestQueue requestQueue;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreference sharedPreference;
    TabFragmentCart update;
    String UserId = "",UserName="";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView distributorName,totalPrice;
        public RecyclerView recyclerview_cart_items;
        public Button btnPlaceOrder,btnShareOrder;

        public MyViewHolder(View view) {
            super(view);

            Log.d("(((((( #: ",""+ cartList.size());
            distributorName = (TextView) view.findViewById(R.id.tv_cart_distributor_name);
            totalPrice= (TextView) view.findViewById(R.id.tv_cart_total_price);
            recyclerview_cart_items = (RecyclerView)view.findViewById(R.id.recyclerview_cart_items);
            btnPlaceOrder = (Button)view.findViewById(R.id.btnPlaceOrder);
            btnShareOrder = (Button)view.findViewById(R.id.btnShareOrder);
            Log.d("$$$$$$ #: ",""+ cartList.size());
        }
    }


    public CartAdapter(List<Cart> cartList, Activity activity, String UserId, String UserName, TabFragmentCart one) {

        Log.d("construtor #: ",""+ cartList.size());
        this.cartList = cartList;
        this.thisActivity = activity;
        this.UserId = UserId;
        this.UserName = UserName;
        this.update = one;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Log.d("!!!!!!!! #: ",""+ cartList.size());
        final Cart cart = cartList.get(position);
        holder.distributorName.setText(cart.getDistributorName());
        holder.totalPrice.setText(cart.getTotalPrice());
        ArrayList<CartProduct> cartProductList = new ArrayList<>();
        cartProductList = cart.getItems();
        CartProductAdapter mCartProdAdapter = new CartProductAdapter(cartProductList,thisActivity);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        holder.recyclerview_cart_items.setLayoutManager(mLayoutManager);

        mCartProdAdapter.notifyDataSetChanged();
        holder.recyclerview_cart_items.setAdapter(mCartProdAdapter);
        holder.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder(UserId, cart.getDistributorId());
               // Toast.makeText(thisActivity,"welcome : "+holder.distributorName.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    private void placeOrder(final String UserId, final String DistributorId) {
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        final String URL = Constants.APP_URL + Constants.URL_PLACEORDER;
        Log.d("URL #: ", URL);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response #: ", response);
                        try {
                            JSONObject JsonResult = new JSONObject(response);
                            if(JsonResult.getBoolean("result")){
                                update.getCartDetails(update.getView());
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
                params.put("businessName",UserName);
                params.put("distributorId",DistributorId);
                params.put("orderType","MOBORDER");
                return params;
            }
        };
        requestQueue.add(postRequest);
    }



}