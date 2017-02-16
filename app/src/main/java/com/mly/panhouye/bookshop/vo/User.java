package com.mly.panhouye.bookshop.vo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class User extends BmobUser {

    private BmobFile userIcon;

    public BmobFile getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(BmobFile userIcon) {
        this.userIcon = userIcon;
    }
}
