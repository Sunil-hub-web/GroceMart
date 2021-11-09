package com.example.grocemart.modelclass;

public class Variation_ModelClass {

    String variation_Id,variation_unit,variation_mrpPrice,variation_salesPrice,variation_discount;

    public Variation_ModelClass(String variation_Id, String variation_unit, String variation_mrpPrice, String variation_salesPrice, String variation_discount) {
        this.variation_Id = variation_Id;
        this.variation_unit = variation_unit;
        this.variation_mrpPrice = variation_mrpPrice;
        this.variation_salesPrice = variation_salesPrice;
        this.variation_discount = variation_discount;
    }

    public String getVariation_Id() {
        return variation_Id;
    }

    public void setVariation_Id(String variation_Id) {
        this.variation_Id = variation_Id;
    }

    public String getVariation_unit() {
        return variation_unit;
    }

    public void setVariation_unit(String variation_unit) {
        this.variation_unit = variation_unit;
    }

    public String getVariation_mrpPrice() {
        return variation_mrpPrice;
    }

    public void setVariation_mrpPrice(String variation_mrpPrice) {
        this.variation_mrpPrice = variation_mrpPrice;
    }

    public String getVariation_salesPrice() {
        return variation_salesPrice;
    }

    public void setVariation_salesPrice(String variation_salesPrice) {
        this.variation_salesPrice = variation_salesPrice;
    }

    public String getVariation_discount() {
        return variation_discount;
    }

    public void setVariation_discount(String variation_discount) {
        this.variation_discount = variation_discount;
    }
}
