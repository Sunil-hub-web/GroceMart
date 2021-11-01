package com.example.grocemart.modelclass;

public class SubCategory_ModelClass {

    String subcategoryID,subcategoryName;

    public SubCategory_ModelClass(String subcategoryID, String subcategoryName) {
        this.subcategoryID = subcategoryID;
        this.subcategoryName = subcategoryName;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public String toString() {
        return "SubCategory_ModelClass{" +
                "subcategoryID='" + subcategoryID + '\'' +
                ", subcategoryName='" + subcategoryName + '\'' +
                '}';
    }
}
