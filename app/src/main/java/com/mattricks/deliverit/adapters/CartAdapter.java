package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;

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
    Activity thisActivity;
    RequestQueue requestQueue;
    RecyclerView.LayoutManager mLayoutManager;
    TabFragmentCart update;
    String UserId = "", UserName = "", UserMobile = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView distributorName, totalPrice;
        public RecyclerView recyclerview_cart_items;
        public Button btnPlaceOrder, btnShareOrder;

        public MyViewHolder(View view) {
            super(view);

            distributorName = (TextView) view.findViewById(R.id.tv_cart_distributor_name);
            totalPrice = (TextView) view.findViewById(R.id.tv_cart_total_price);
            recyclerview_cart_items = (RecyclerView) view.findViewById(R.id.recyclerview_cart_items);
            btnPlaceOrder = (Button) view.findViewById(R.id.btnPlaceOrder);
            btnShareOrder = (Button) view.findViewById(R.id.btnShareOrder);
        }
    }


    public CartAdapter(List<Cart> cartList, Activity activity, String UserId, String UserName, String UserMobile, TabFragmentCart one) {

        this.cartList = cartList;
        this.thisActivity = activity;
        this.UserId = UserId;
        this.UserName = UserName;
        this.update = one;
        this.UserMobile = UserMobile;
    }

    View v;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Cart cart = cartList.get(position);
        holder.distributorName.setText(cart.getDistributorName());
        holder.totalPrice.setText(cart.getTotalPrice());
        ArrayList<CartProduct> cartProductList = new ArrayList<>();
        cartProductList = cart.getItems();
        CartProductAdapter mCartProdAdapter = new CartProductAdapter(cartProductList, thisActivity);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        holder.recyclerview_cart_items.setLayoutManager(mLayoutManager);

        mCartProdAdapter.notifyDataSetChanged();
        holder.recyclerview_cart_items.setAdapter(mCartProdAdapter);
        holder.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder(UserId, cart.getDistributorId(),"PlaceOrder");
                // Toast.makeText(thisActivity,"welcome : "+holder.distributorName.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShareOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cart
                shareOrder(UserId, UserName, cart);
                placeOrder(UserId, cart.getDistributorId(),"ShareOrder");
            }
        });


    }

    private void shareOrder(String userId, String userName, Cart cart) {
        String shareOrder = "\n New Order by " + userName + "\n";
        shareOrder = shareOrder + "Mobile Number : " + UserMobile + "\n";
        int total = 0;
        String itemsToShare = "\n";
        ArrayList<CartProduct> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemsToShare = itemsToShare + (i + 1) + "." + items.get(i).getName() + "\n";
            itemsToShare = itemsToShare + "Qty." + items.get(i).getQuantity();
            itemsToShare = itemsToShare + "* Rs." + items.get(i).getDistributorPrice();
            int totalPerItem = (Integer.valueOf(items.get(i).getQuantity()) * Integer.valueOf(items.get(i).getDistributorPrice()));
            itemsToShare = itemsToShare + "= Rs. " + totalPerItem + "\n\n";
            total = total + totalPerItem;
        }
        itemsToShare = itemsToShare + "Total : Rs." + total;
        shareOrder = shareOrder + itemsToShare;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                shareOrder);
        sendIntent.setType("text/plain");
        thisActivity.startActivity(sendIntent);
        Toast.makeText(thisActivity, "Order Shared to " + cart.getDistributorName(),
                Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private void placeOrder(final String UserId, final String DistributorId, final String OrderType) {
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        final String URL = Constants.APP_URL + Constants.URL_PLACEORDER;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JsonResult = new JSONObject(response);
                            if (JsonResult.getBoolean("result")) {
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
                params.put("businessId", UserId);
                params.put("businessName", UserName);
                params.put("distributorId", DistributorId);
                params.put("orderType", OrderType);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }


}