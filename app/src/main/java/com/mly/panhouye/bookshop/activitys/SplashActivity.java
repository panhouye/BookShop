package com.mly.panhouye.bookshop.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mly.panhouye.bookshop.MainActivity;
import com.mly.panhouye.bookshop.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        },3000);
    }
    private void start(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //请求动态权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST);
        }else{
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:
                for (int i = 0; i < grantResults.length; i++) {
                    //检查权限是否被拒绝
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(SplashActivity.this, "本应用需要申请读写sdcard权限来访问本地音乐文件", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
