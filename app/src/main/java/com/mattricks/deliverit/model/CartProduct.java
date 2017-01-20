package com.mattricks.deliverit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mahasagar on 18/1/17.
 */
public class CartProduct implements Parcelable {

    public String productId, brand, form, marketingCompanyName, name, productImgUrl, packSize, packType;
    public String distributorPrice, MRP, distributorId, distributorName, quantity, totalPricePerProduct;

    public CartProduct() {
    }

    public CartProduct(String productId, String brand, String form, String marketingCompanyName, String name, String productImgUrl,
                       String packSize, String packType,
                       String distributorPrice, String MRP, String distributorId, String distributorName, String quantity, String totalPricePerProduct) {
        this.productId = productId;
        this.brand = brand;
        this.form = form;
        this.marketingCompanyName = marketingCompanyName;
        this.name = name;
        this.productImgUrl = productImgUrl;
        this.packSize = packSize;
        this.packType = packType;
        this.distributorPrice = distributorPrice;
        this.MRP = MRP;
        this.distributorId = distributorId;
        this.distributorName = distributorName;
        this.quantity = quantity;
        this.totalPricePerProduct = totalPricePerProduct;
    }

    protected CartProduct(Parcel in) {
        productId = in.readString();
        brand = in.readString();
        form = in.readString();
        marketingCompanyName = in.readString();
        name = in.readString();
        productImgUrl = in.readString();
        packSize = in.readString();
        packType = in.readString();
        distributorPrice = in.readString();
        MRP = in.readString();
        distributorId = in.readString();
        distributorName = in.readString();
        quantity = in.readString();
        totalPricePerProduct = in.readString();
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setMarketingCompanyName(String marketingCompanyName) {
        this.marketingCompanyName = marketingCompanyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public void setDistributorPrice(String distributorPrice) {
        this.distributorPrice = distributorPrice;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getBrand() {
        return brand;
    }

    public String getForm() {
        return form;
    }

    public String getMarketingCompanyName() {
        return marketingCompanyName;
    }

    public String getName() {
        return name;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public String getPackSize() {
        return packSize;
    }

    public String getPackType() {
        return packType;
    }

    public String getDistributorPrice() {
        return distributorPrice;
    }

    public String getMRP() {
        return MRP;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPricePerProduct() {
        return totalPricePerProduct;
    }

    public void setTotalPricePerProduct(String totalPricePerProduct) {
        this.totalPricePerProduct = totalPricePerProduct;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(brand);
        dest.writeString(form);
        dest.writeString(marketingCompanyName);
        dest.writeString(name);
        dest.writeString(productImgUrl);
        dest.writeString(packSize);
        dest.writeString(packType);
        dest.writeString(distributorPrice);
        dest.writeString(MRP);
        dest.writeString(distributorId);
        dest.writeString(distributorName);
        dest.writeString(quantity);
        dest.writeString(totalPricePerProduct);
    }
}