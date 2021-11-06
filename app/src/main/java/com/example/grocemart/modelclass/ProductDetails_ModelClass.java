package com.example.grocemart.modelclass;

public class ProductDetails_ModelClass {

    String productId,productName,productQuantity,productImage,productPrice;

    public ProductDetails_ModelClass(String productId, String productName, String productQuantity,
                                     String productImage, String productPrice) {

        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.productPrice = productPrice;
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

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
