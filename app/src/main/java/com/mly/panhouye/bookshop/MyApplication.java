package com.mly.panhouye.bookshop;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */

public class MyApplication extends Application {
    public static Context context;
    public static ArrayList<Activity> activities = new ArrayList<>();//存放所有activity的引用
    public static int screenWidth,screenHeight;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //初始化 bmob
        Bmob.initialize(this, "e04fe50e49431aa0b875d1e5506fe998");
        context = getApplicationContext();
    }

    public static void release(){
        activities = null;
        context = null;
        Fresco.shutDown();
    }
}
