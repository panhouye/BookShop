package com.mly.panhouye.bookshop.vo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class Comment extends BmobObject {

    private String userId;
    private String bookInfoId;
    private int star;
    private String content;
    private BmobFile pic;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookInfoId() {
        return bookInfoId;
    }

    public void setBookInfoId(String bookInfoId) {
        this.bookInfoId = bookInfoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
