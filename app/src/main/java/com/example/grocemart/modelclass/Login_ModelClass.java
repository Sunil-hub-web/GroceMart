package com.example.grocemart.modelclass;

public class Login_ModelClass {

    String id,name,email,mobileNo,password,wallet_Amount;

    public Login_ModelClass(String id, String name, String email, String mobileNo, String password,String wallet_Amount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.password = password;
        this.wallet_Amount = wallet_Amount;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWallet_Amount() {
        return wallet_Amount;
    }

    public void setWallet_Amount(String wallet_Amount) {
        this.wallet_Amount = wallet_Amount;
    }

    @Override
    public String toString() {
        return "Login_ModelClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile_no='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
