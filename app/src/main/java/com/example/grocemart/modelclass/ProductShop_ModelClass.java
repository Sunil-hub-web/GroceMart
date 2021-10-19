package com.example.grocemart.modelclass;

public class ProductShop_ModelClass {

    int image;
    String productName,shopName,address;

    public ProductShop_ModelClass(int image, String productName, String shopName, String address) {
        this.image = image;
        this.productName = productName;
        this.shopName = shopName;
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
