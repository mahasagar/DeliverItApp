package com.mattricks.deliverit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sagar on 31/1/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView itemImage;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            itemImage = (ImageView) view.findViewById(R.id.image);
        }
    }


    public ProductAdapter(List<Product> productList) {

        Log.d("construtor #: ",""+ productList.size());
        this.productList = productList;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);

        Log.d("here #: ",""+ productList.size());
        Log.d("product.getName() #: ",""+ product.getName());
        holder.name.setText(product.getName());
        Picasso.with(v.getContext()).load(product.getProductImgUrl()).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}