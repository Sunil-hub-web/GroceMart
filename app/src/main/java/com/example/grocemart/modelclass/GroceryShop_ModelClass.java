package com.example.grocemart.modelclass;

public class GroceryShop_ModelClass {

    String shopid,shopName,ShopBanner,ShopLogo;

    public GroceryShop_ModelClass(String shopid, String shopName, String shopBanner, String shopLogo) {
        this.shopid = shopid;
        this.shopName = shopName;
        ShopBanner = shopBanner;
        ShopLogo = shopLogo;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopBanner() {
        return ShopBanner;
    }

    public void setShopBanner(String shopBanner) {
        ShopBanner = shopBanner;
    }

    public String getShopLogo() {
        return ShopLogo;
    }

    public void setShopLogo(String shopLogo) {
        ShopLogo = shopLogo;
    }
}
