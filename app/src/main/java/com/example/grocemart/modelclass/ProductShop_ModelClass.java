package com.example.grocemart.modelclass;

import java.sql.Array;
import java.util.ArrayList;

public class ProductShop_ModelClass {

    String productId,productName,productDesc,productImage,shopId,shopName,shopAddress,
            variationId,unit,mrpPrice,salesPrice,discount;
    ArrayList<Variation_ModelClass> variation;

    public ProductShop_ModelClass(String productId, String productName, String productDesc,
                                  String productImage, String shopId, String shopName,
                                  String shopAddress, String variationId, String unit, String mrpPrice,
                                  String salesPrice, String discount,
                                  ArrayList<Variation_ModelClass> variation) {

        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productImage = productImage;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.variationId = variationId;
        this.unit = unit;
        this.mrpPrice = mrpPrice;
        this.salesPrice = salesPrice;
        this.discount = discount;
        this.variation = variation;
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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public ArrayList<Variation_ModelClass> getVariation() {
        return variation;
    }

    public void setVariation(ArrayList<Variation_ModelClass> variation) {
        this.variation = variation;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ProductShop_ModelClass{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productImage='" + productImage + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", variationId='" + variationId + '\'' +
                ", unit='" + unit + '\'' +
                ", mrpPrice='" + mrpPrice + '\'' +
                ", salesPrice='" + salesPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", variation=" + variation +
                '}';
    }
}
