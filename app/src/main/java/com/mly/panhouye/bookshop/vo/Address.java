package com.mly.panhouye.bookshop.vo;

import cn.bmob.v3.BmobObject;
/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class Address extends BmobObject {

    private String userId;
    private String address;
    private String username;
    private String phoneNumber;
    private boolean isDefault;

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
