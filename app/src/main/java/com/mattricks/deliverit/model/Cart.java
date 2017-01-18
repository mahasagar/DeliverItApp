package com.mattricks.deliverit.model;

import java.util.ArrayList;

/**
 * Created by mahasagar on 17/1/17.
 */
public class Cart {
    public String DistributorName;
    public String DistributorId;
    public String TotalPrice;
    public ArrayList<CartProduct> Items;

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public ArrayList<CartProduct> getItems() {
        return Items;
    }

    public void setItems(ArrayList<CartProduct> items) {
        Items = items;
    }
}
