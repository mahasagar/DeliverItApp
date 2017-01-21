package com.mattricks.deliverit.adapters;

public class ListItem {
    public String productName, distributorPrice, distributorName;

    public String getDistributorName() {
        return distributorName;
    }

    public String getDistributorPrice() {
        return distributorPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public void setDistributorPrice(String distributorPrice) {
        this.distributorPrice = distributorPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

