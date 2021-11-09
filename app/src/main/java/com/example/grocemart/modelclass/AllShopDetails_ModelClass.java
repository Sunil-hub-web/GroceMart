package com.example.grocemart.modelclass;

import java.util.ArrayList;

public class AllShopDetails_ModelClass {

    String shopId,shopName,shopImage,shopAddress;
    ArrayList<CategoryName_ModelClass> category;

    public AllShopDetails_ModelClass(String shopId, String shopName, String shopImage,
                                     String shopAddress, ArrayList<CategoryName_ModelClass> category) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopImage = shopImage;
        this.shopAddress = shopAddress;
        this.category = category;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public ArrayList<CategoryName_ModelClass> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<CategoryName_ModelClass> category) {
        this.category = category;
    }
}
