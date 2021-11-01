package com.example.grocemart.modelclass;

public class CartItem {

    public String product_id, product_name, productimage, varient_id, unit,unit_name,mrp_Price, sales_price, quantity, itemtotal;

    public CartItem(String product_id, String product_name, String productimage, String varient_id,
                    String unit, String unit_name, String mrp_Price, String sales_price,
                    String quantity, String itemtotal) {

        this.product_id = product_id;
        this.product_name = product_name;
        this.productimage = productimage;
        this.varient_id = varient_id;
        this.unit = unit;
        this.unit_name = unit_name;
        this.mrp_Price = mrp_Price;
        this.sales_price = sales_price;
        this.quantity = quantity;
        this.itemtotal = itemtotal;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getVarient_id() {
        return varient_id;
    }

    public void setVarient_id(String varient_id) {
        this.varient_id = varient_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getMrp_Price() {
        return mrp_Price;
    }

    public void setMrp_Price(String mrp_Price) {
        this.mrp_Price = mrp_Price;
    }

    public String getSales_price() {
        return sales_price;
    }

    public void setSales_price(String sales_price) {
        this.sales_price = sales_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(String itemtotal) {
        this.itemtotal = itemtotal;
    }
}
