package com.mly.panhouye.bookshop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    EditText login_tel,login_password;
    ImageView login_psw_visible;
    TextView login_back,login_regist,login_forgetpass;
    Button login_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }
    private void initView() {
        login_tel= (EditText) findViewById(R.id.login_tel);
        login_password= (EditText) findViewById(R.id.login_password);
        login_psw_visible= (ImageView) findViewById(R.id.login_psw_visible);
        login_back= (TextView) findViewById(R.id.login_back);
        login_regist= (TextView) findViewById(R.id.login_regist);
        login_forgetpass= (TextView) findViewById(R.id.login_forgetpass);
        login_sure= (Button) findViewById(R.id.login_sure);

        login_psw_visible.setSelected(false);
        login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    private void initListener() {
        login_psw_visible.setOnClickListener(this);
        login_back.setOnClickListener(this);
        login_regist.setOnClickListener(this);
        login_forgetpass.setOnClickListener(this);
        login_sure.setOnClickListener(this);
    }
    //密码显示标记
    private boolean showPassword = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_psw_visible:
                if (showPassword){
                    login_psw_visible.setSelected(true);
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword = false;
                }else{
                    login_psw_visible.setSelected(false);
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword = true;
                }
                break;
            case R.id.login_back:
                finish();
                break;
            case R.id.login_regist:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.login_forgetpass:
                startActivity(new Intent(this,Reset_paswordActivity.class));
            break;
            case R.id.login_sure:
                login();
                break;
            default:
                break;
        }
    }
    private void login() {
        String phone = login_tel.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号");
            return;
        }
        String password = login_password.getText().toString();
        if(TextUtils.isEmpty(password)){
            showToast("请输入密码");
            return;
        }
        User user = new User();
        user.setUsername(phone);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    showToast("登录成功");
                    setResult(RESULT_OK);
                    finish();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    showToast("登录失败"+e);
                }
            }
        });
    }
}
