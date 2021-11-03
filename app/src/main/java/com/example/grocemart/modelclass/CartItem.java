package com.example.grocemart.modelclass;

public class CartItem {

    public String productId,variationId,shopId,productImage, productName,shopName,unit,
            salePrice,discount,quantity;

    public CartItem(String productId, String variationId, String shopId, String productImage,
                    String productName, String shopName, String unit, String salePrice,
                    String discount, String quantity) {

        this.productId = productId;
        this.variationId = variationId;
        this.shopId = shopId;
        this.productImage = productImage;
        this.productName = productName;
        this.shopName = shopName;
        this.unit = unit;
        this.salePrice = salePrice;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId='" + productId + '\'' +
                ", variationId='" + variationId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productName='" + productName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", unit='" + unit + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", discount='" + discount + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
