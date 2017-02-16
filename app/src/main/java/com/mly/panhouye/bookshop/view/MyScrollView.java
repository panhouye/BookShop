package com.mly.panhouye.bookshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */
public class MyScrollView extends ScrollView {
    private static final String TAG = "MyScrollView";

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event){


//        if(getScrollY()==1989){
//            requestDisallowInterceptTouchEvent(false);
//        }else{
//            requestDisallowInterceptTouchEvent(true);
//        }
//        Log.i(TAG, "onTouchEvent: getScrollY:"+getScrollY());
//        Log.i(TAG, "onTouchEvent: getHeight:"+getMeasuredHeight());
        return super.onTouchEvent(event);
    }
}
