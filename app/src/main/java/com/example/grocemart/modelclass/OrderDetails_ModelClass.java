package com.example.grocemart.modelclass;

import java.util.ArrayList;

public class OrderDetails_ModelClass {

    String order_id,order_status,shiping_type,shipping_charge,payment_mode,subtotal,
            total,delivery_date,timeSlot,name,state,city,pincode,address,phoneNumber;
    ArrayList<ProductDetails_ModelClass> productdetails;

    public OrderDetails_ModelClass(String order_id, String order_status, String shiping_type,
                                   String shipping_charge, String payment_mode, String subtotal,
                                   String total, String delivery_date, String timeSlot,String name,
                                   String state,String city,String pincode,String address,String phoneNumber,
                                   ArrayList<ProductDetails_ModelClass> productdetails ) {

        this.order_id = order_id;
        this.order_status = order_status;
        this.shiping_type = shiping_type;
        this.shipping_charge = shipping_charge;
        this.payment_mode = payment_mode;
        this.subtotal = subtotal;
        this.total = total;
        this.delivery_date = delivery_date;
        this.timeSlot = timeSlot;
        this.name = name;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.productdetails = productdetails;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getShiping_type() {
        return shiping_type;
    }

    public void setShiping_type(String shiping_type) {
        this.shiping_type = shiping_type;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<ProductDetails_ModelClass> getProductdetails() {
        return productdetails;
    }

    public void setProductdetails(ArrayList<ProductDetails_ModelClass> productdetails) {
        this.productdetails = productdetails;
    }
}
