package com.example.grocemart.modelclass;

import java.util.ArrayList;

public class SubCategoryName_ModelClass {

    String subcategoryName;
    ArrayList<AllProduct_ModelClass> allproduct;

    public SubCategoryName_ModelClass(String subcategoryName, ArrayList<AllProduct_ModelClass> allproduct) {
        this.subcategoryName = subcategoryName;
        this.allproduct = allproduct;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public ArrayList<AllProduct_ModelClass> getAllproduct() {
        return allproduct;
    }

    public void setAllproduct(ArrayList<AllProduct_ModelClass> allproduct) {
        this.allproduct = allproduct;
    }
}
