package com.mattricks.deliverit.model;

/**
 * Created by mahasagar on 15/1/17.
 */

public class Distributor {
    public String DistributorName;
    public String DistributorID;
    public String DistributorPrice;

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public void setDistributorID(String distributorID) {
        DistributorID = distributorID;
    }

    public void setDistributorPrice(String distributorPrice) {
        distributorPrice = distributorPrice;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public String getDistributorPrice() {
        return DistributorPrice;
    }
}
