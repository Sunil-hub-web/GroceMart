package com.example.grocemart.modelclass;

public class SubCategoryName_ModelClass {

    String subcategoryName;

    public SubCategoryName_ModelClass(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public String toString() {
        return "SubCategoryName_ModelClass{" +
                "subcategoryName='" + subcategoryName + '\'' +
                '}';
    }
}
