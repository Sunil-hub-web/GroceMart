package com.example.grocemart.modelclass;

public class Variation_ModelClass {

    String variationId,unit,unitName,mrpPrice,salesPrice,discount;

    public Variation_ModelClass(String variationId, String unit,
                                String unitName, String mrpPrice,
                                String salesPrice, String discount) {
        this.variationId = variationId;
        this.unit = unit;
        this.unitName = unitName;
        this.mrpPrice = mrpPrice;
        this.salesPrice = salesPrice;
        this.discount = discount;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
}
