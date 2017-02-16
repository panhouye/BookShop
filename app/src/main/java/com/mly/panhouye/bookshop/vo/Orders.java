package com.mly.panhouye.bookshop.vo;

import cn.bmob.v3.BmobObject;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class Orders extends BmobObject {

    private String userId;//用户编号
    private String bookInfoId;//图书ID
    private int status;//状态 (未付款，已付款，已发货，配送中，已收货，已评价)
    private double subtotal;//小计
    private int total;//购买数量
    private double discountPrice;//单价
    private String bookName;//书名
    private String bookImage;//书图片路径
    private String orderId;// 订单编号
    private String buyDate;//购买时间
    private double freight;//运费
    private Address address;//收货地址

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getBookInfoId() {
        return bookInfoId;
    }

    public void setBookInfoId(String bookInfoId) {
        this.bookInfoId = bookInfoId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "bookImage='" + bookImage + '\'' +
                ", userId='" + userId + '\'' +
                ", bookInfoId='" + bookInfoId + '\'' +
                ", status=" + status +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", discountPrice=" + discountPrice +
                ", bookName='" + bookName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", buyDate='" + buyDate + '\'' +
                '}';
    }
}
