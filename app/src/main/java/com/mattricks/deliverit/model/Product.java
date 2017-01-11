package com.mattricks.deliverit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sagar on 31/1/16.
 */
public class Product implements Parcelable {

    public String _id, brand,form,marketingCompanyName,name,productImgUrl,packSize, packType;
    public String distributorPrice,MRP,distributorId,distributorName;

    public Product() {
    }

    public Product(String _id,String brand,String form,String marketingCompanyName,String name,String productImgUrl,
                   String packSize,String packType,
                   String distributorPrice,String MRP,String distributorId,String distributorName) {
        this._id = _id;
        this.brand = brand;
        this.form = form;
        this.marketingCompanyName=marketingCompanyName;
        this.name = name;
        this.productImgUrl = productImgUrl;
        this.packSize = packSize;
        this.packType = packType;
        this.distributorPrice=distributorPrice;
        this.MRP = MRP;
        this.distributorId = distributorId;
        this.distributorName=distributorName;
    }

    protected Product(Parcel in) {
        _id = in.readString();
        brand =in.readString();
        form = in.readString();
        marketingCompanyName=in.readString();
        name = in.readString();
        productImgUrl = in.readString();
        packSize = in.readString();
        packType = in.readString();
        distributorPrice= in.readString();
        MRP = in.readString();
        distributorId = in.readString();
        distributorName= in.readString();
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String get_id() {
        return _id;
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
        dest.writeString(_id);
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
    }
}