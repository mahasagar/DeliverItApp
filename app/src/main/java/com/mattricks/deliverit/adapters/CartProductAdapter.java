package com.mattricks.deliverit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.CartProduct;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahasagar on 18/1/17.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.MyViewHolder> {

    private List<CartProduct> cartProductList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemQuantity,itemPrice,itemTotal;
        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.cart_item_product_name);
            itemQuantity = (TextView) view.findViewById(R.id.cart_item_product_qty);
            itemPrice = (TextView) view.findViewById(R.id.cart_item_product_price);
            itemTotal = (TextView) view.findViewById(R.id.cart_item_product_total);
        }
    }


    public CartProductAdapter(List<CartProduct> cartProductList) {

        Log.d("construtor #: ",""+ cartProductList.size());
        this.cartProductList = cartProductList;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_product, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CartProduct cartProduct = cartProductList.get(position);
        Log.d("here #: ",""+ cartProductList.size());
        holder.itemName.setText(cartProduct.getName());
        holder.itemQuantity.setText(cartProduct.getQuantity());
        holder.itemPrice.setText("* "+cartProduct.getDistributorPrice());
        holder.itemTotal.setText("= "+cartProduct.getTotalPricePerProduct());
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }


}