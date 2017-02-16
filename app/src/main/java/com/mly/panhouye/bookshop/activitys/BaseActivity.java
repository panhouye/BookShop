package com.mly.panhouye.bookshop.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mly.panhouye.bookshop.MyApplication;

/**
 * Created by panchengjia on 2017/1/16 0016.
 */

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.activities.add(this);
        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyApplication.screenWidth = dm.widthPixels;
        MyApplication.screenHeight = dm.heightPixels;

    }
    public void backClick(View view){
//        App.activities.remove(this);
        finish();
    }

    /**
     * 再按一次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                closeAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 关闭所有Activity界面
     */
    public static void closeAll(){
        for (int i=0;i<MyApplication.activities.size();i++){
            MyApplication.activities.get(i).finish();
        }
        MyApplication.release();
    }

    /**
     * Toast提示
     * @param text
     */
    public void showToast(String text){
        Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.activities.remove(this);
//        RefWatcher refWatcher = App.getRefWatcher(this);
//        refWatcher.watch(this);
    }
}
