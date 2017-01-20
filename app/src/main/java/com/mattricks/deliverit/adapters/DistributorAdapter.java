package com.mattricks.deliverit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.model.Distributor;

import java.util.List;


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