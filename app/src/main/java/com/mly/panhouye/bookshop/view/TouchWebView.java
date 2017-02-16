package com.mly.panhouye.bookshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class TouchWebView extends WebView {
    private static final String TAG = "TouchWebView";

    public TouchWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TouchWebView(Context context) {
        super(context);
    }


    float startY = 0f;
    float endY = 0f;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //完美解决webView 与 ScrollView的滚动冲突
//        requestDisallowInterceptTouchEvent(true);
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            startY = event.getY();
        }
        if(action == MotionEvent.ACTION_MOVE){
            endY = event.getY();
            if(startY>endY){
                //请求拦截事件,本身处理
                requestDisallowInterceptTouchEvent(true);
            }
            else if(startY<endY && getScrollY()==0){
                //事件处理由父控制处理
                requestDisallowInterceptTouchEvent(false);
            }else{
                requestDisallowInterceptTouchEvent(true);
            }
        }

        return super.onTouchEvent(event);
    }
}