package com.example.grocemart.modelclass;

public class DifferentShopDetails_ModelClass {

    String shopId,shopName,shopBanner,shopLogo,shopAddress,City,State,Country;

    public DifferentShopDetails_ModelClass(String shopId, String shopName, String shopBanner,
                                           String shopLogo, String shopAddress, String city,
                                           String state, String country) {

        this.shopId = shopId;
        this.shopName = shopName;
        this.shopBanner = shopBanner;
        this.shopLogo = shopLogo;
        this.shopAddress = shopAddress;
        City = city;
        State = state;
        Country = country;
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

    public String getShopBanner() {
        return shopBanner;
    }

    public void setShopBanner(String shopBanner) {
        this.shopBanner = shopBanner;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
