package com.mattricks.deliverit.model;

/**
 * Created by mahasagar on 19/1/17.
 */
public class Order {
    String DistributorName;
    String OrderId;
    String OrderTotal;
    String OrderDate;
    String Status;

    public String getDistributorName() {
        return DistributorName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public String getStatus() {
        return Status;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
