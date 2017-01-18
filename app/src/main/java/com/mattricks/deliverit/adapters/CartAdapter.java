package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.model.Cart;
import com.mattricks.deliverit.model.CartProduct;
import com.mattricks.deliverit.model.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahasagar on 17/1/17.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Cart> cartList;
    private List<CartProduct> cartProductsList;
    Activity thisActivity;
    RecyclerView.LayoutManager mLayoutManager;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView distributorName,totalPrice;
        public RecyclerView recyclerview_cart_items;

        public MyViewHolder(View view) {
            super(view);

            Log.d("(((((( #: ",""+ cartList.size());
            distributorName = (TextView) view.findViewById(R.id.tv_cart_distributor_name);
            totalPrice= (TextView) view.findViewById(R.id.tv_cart_total_price);
            recyclerview_cart_items = (RecyclerView)view.findViewById(R.id.recyclerview_cart_items);

            Log.d("$$$$$$ #: ",""+ cartList.size());
        }
    }


    public CartAdapter(List<Cart> cartList,Activity activity) {

        Log.d("construtor #: ",""+ cartList.size());
        this.cartList = cartList;
        this.thisActivity = activity;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
       /* itemView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));*/

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
        CartProductAdapter mCartProdAdapter = new CartProductAdapter(cartProductList);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        holder.recyclerview_cart_items.setLayoutManager(mLayoutManager);

        mCartProdAdapter.notifyDataSetChanged();
        holder.recyclerview_cart_items.setAdapter(mCartProdAdapter);
       /* mAdapter = new CartAdapter(cartList);
        recyclerViewCart.setAdapter(mAdapter);
        restoreRecycleView();
        mAdapter.notifyDataSetChanged();*/

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

}