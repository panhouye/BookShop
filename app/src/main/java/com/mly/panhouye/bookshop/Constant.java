package com.mly.panhouye.bookshop;

/**
 * Created by panchengjia on 2017/1/17 0017.
 */

public class Constant {
    private Constant(){};

    //缓存路径
    public static final String CACHE_PATH = "/MLYBookStore";
    //文件地址前缀
    public static final String BMOBFILE_ROOT="http://file.bmob.cn/";
//    //数据库名
//    public static final String DB_NAME="CodingBookStore.db";

    //订单状态:
    public static final int ORDER_NON_PAYMENT = 0x1;//未付款
    public static final int ORDER_PAYMENTS_RECEIVED = 0x2;//已付款
    public static final int ORDER_DELIVERED = 0x3;//已发货
    //    public static final int ORDER_DISTRIBUTION = 0x4;//配送中
    public static final int ORDER_RECEIVED_GOODS = 0x4;//已收货
    public static final int ORDER_HAS_BEEN_EVALUATED = 0x5;//已评价
}
