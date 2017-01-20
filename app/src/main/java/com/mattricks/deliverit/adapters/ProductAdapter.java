package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;
    String strMrp;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPackType, itemMRP;
        public ImageView itemImage;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.itemName);
            itemImage = (ImageView) view.findViewById(R.id.itemImage);
            itemPackType = (TextView) view.findViewById(R.id.itemPackType);
            itemMRP = (TextView) view.findViewById(R.id.txtViewMRP);
        }
    }


    public ProductAdapter(List<Product> productList,Activity activity) {

        this.productList = productList;
        strMrp = activity.getResources().getString(R.string.strMrp);
    }

    View v;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.itemName.setText(product.getName());
        holder.itemPackType.setText(product.getPackType());
        String mrp = strMrp + product.getMRP();
        holder.itemMRP.setText(mrp);
        Picasso.with(v.getContext()).load(product.getProductImgUrl()).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}