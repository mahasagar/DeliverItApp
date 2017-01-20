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
import com.mattricks.deliverit.model.Distributor;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahasagar on 15/1/17.
 */
public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.MyViewHolder> {

    private List<Distributor> distributorList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView distributorName, distributorPrice;

        public MyViewHolder(View view) {
            super(view);
            distributorName = (TextView) view.findViewById(R.id.tv_distributorname);
            distributorPrice = (TextView) view.findViewById(R.id.tv_distributorprice);

        }
    }


    public DistributorAdapter(List<Distributor> distributorList) {

        this.distributorList = distributorList;
    }

    View v;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_distributor, parent, false);
        itemView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Distributor distributor = distributorList.get(position);
        holder.distributorName.setText(distributor.getDistributorName());
        holder.distributorPrice.setText(distributor.getDistributorPrice());
    }

    @Override
    public int getItemCount() {
        return distributorList.size();
    }

}