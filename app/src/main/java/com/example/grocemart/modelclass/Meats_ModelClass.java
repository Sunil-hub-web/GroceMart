package com.example.grocemart.modelclass;

public class Meats_ModelClass {

    String productId,productName,productDesc,productImage;

    public Meats_ModelClass(String productId, String productName, String productDesc, String productImage) {

        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
