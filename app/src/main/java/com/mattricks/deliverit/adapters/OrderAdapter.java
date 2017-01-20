package com.mattricks.deliverit.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.TabFragmentOrders;
import com.mattricks.deliverit.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private final Activity thisActivity;
    private List<Order> orderList;
    TabFragmentOrders orderFrag;
    String UserId = "",strOrderTotal="",strOrderStatus="",strOrderId ="";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemDistributorName, itemOrderId, itemOrderTotal, itemOrderDate, itemOrderStatus;

        public MyViewHolder(View view) {
            super(view);
            itemDistributorName = (TextView) view.findViewById(R.id.itemDistributorName);
            itemOrderTotal = (TextView) view.findViewById(R.id.itemOrderTotal);
            itemOrderDate = (TextView) view.findViewById(R.id.itemOrderDate);
            itemOrderStatus = (TextView) view.findViewById(R.id.itemOrderStatus);
            itemOrderId = (TextView) view.findViewById(R.id.itemOrderId);
        }
    }

    public OrderAdapter(List<Order> orderList, Activity activity, String UserId, TabFragmentOrders orderFrag) {
        this.orderList = orderList;
        this.thisActivity = activity;
        this.UserId = UserId;
        this.orderFrag = orderFrag;
        strOrderTotal =thisActivity.getResources().getString(R.string.strOrderTotal);
        strOrderStatus =thisActivity.getResources().getString(R.string.strOrderStatus);
        strOrderId =thisActivity.getResources().getString(R.string.strOrderId);
    }

    View v;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Order order = orderList.get(position);

        holder.itemDistributorName.setText(order.getDistributorName());
        String total = strOrderTotal + order.getOrderTotal();
        holder.itemOrderTotal.setText(total);
        String formatedDate = FormatedDate(order.getOrderDate());
        holder.itemOrderDate.setText(formatedDate);
        String status =  strOrderStatus+ order.getStatus();
        holder.itemOrderStatus.setText(status);
        String orderId = strOrderId+ order.getOrderId();
        holder.itemOrderId.setText(orderId);

    }

    private String FormatedDate(String input) {
        TimeZone ist = TimeZone.getTimeZone("IST");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
        f.setTimeZone(ist);
        GregorianCalendar cal = new GregorianCalendar(ist);
        try {
            cal.setTime(f.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(cal.getTime());
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

}