package com.example.grocemart.modelclass;

public class Banner_ModelClass {

    String bannerId,bannerTitle,bannerImage;

    public Banner_ModelClass(String bannerId, String bannerTitle, String bannerImage) {
        this.bannerId = bannerId;
        this.bannerTitle = bannerTitle;
        this.bannerImage = bannerImage;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }
}
