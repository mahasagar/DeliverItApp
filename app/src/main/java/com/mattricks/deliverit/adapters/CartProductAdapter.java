package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.model.CartProduct;
import java.util.List;
/**
 * Created by mahasagar on 18/1/17.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.MyViewHolder> {

    private List<CartProduct> cartProductList;
    Activity thisActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemQuantity, itemPrice, itemTotal;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.cart_item_product_name);
            itemQuantity = (TextView) view.findViewById(R.id.cart_item_product_qty);
            itemPrice = (TextView) view.findViewById(R.id.cart_item_product_price);
            itemTotal = (TextView) view.findViewById(R.id.cart_item_product_total);
        }
    }


    public CartProductAdapter(List<CartProduct> cartProductList, Activity thisActivity) {

        this.cartProductList = cartProductList;
        this.thisActivity = thisActivity;
    }

    View v;

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
        holder.itemName.setText(cartProduct.getName());
        String qty = thisActivity.getResources().getString(R.string.strQuantity) + cartProduct.getQuantity();
        holder.itemQuantity.setText(qty);
        String price = thisActivity.getResources().getString(R.string.strPrice) + cartProduct.getDistributorPrice();
        holder.itemPrice.setText(price);
        String total = thisActivity.getResources().getString(R.string.strProductTotal) + cartProduct.getTotalPricePerProduct();
        holder.itemTotal.setText(total);
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }


}