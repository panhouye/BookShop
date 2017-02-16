package com.mly.panhouye.bookshop.vo;

import cn.bmob.v3.BmobObject;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class Category extends BmobObject {


    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
