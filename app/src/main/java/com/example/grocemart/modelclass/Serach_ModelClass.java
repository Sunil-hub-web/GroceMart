package com.example.grocemart.modelclass;


public class Serach_ModelClass {

    String id,name,price,shopname,desc,image;

    public Serach_ModelClass(String id, String name, String price, String shopname, String desc,String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shopname = shopname;
        this.desc = desc;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
