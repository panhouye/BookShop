package com.mly.panhouye.bookshop.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.User;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    TextView register_back,register_sendmsg;
    Button register_sure;
    EditText register_tel,register_password,register_verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initView() {
        register_back= (TextView) findViewById(R.id.register_back);
        register_sendmsg= (TextView) findViewById(R.id.register_sendmsg);
        register_sure= (Button) findViewById(R.id.register_sure);
        register_tel= (EditText) findViewById(R.id.register_tel);
        register_password= (EditText) findViewById(R.id.register_password);
        register_verification= (EditText) findViewById(R.id.register_verification);
    }

    private void initListener() {
        register_back.setOnClickListener(this);
        register_sendmsg.setOnClickListener(this);
        register_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_back:
                finish();
                break;
            case R.id.register_sendmsg:
                sendVerification();//发送验证码
                break;
            case R.id.register_sure:
                submitValidateNumber();
                break;
        }
    }

    private void sendVerification() {
        String phone =  register_tel.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号码");
            return;
        }
        BmobSMS.requestSMSCode(phone,"模板名称", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于后续的查询本次短信发送状态
                }
            }
        });
    }
    /**
     * 提交验证码
     */
    private void submitValidateNumber() {
        String sms_code =  register_verification.getText().toString();
        if(TextUtils.isEmpty(sms_code)){
            showToast("请输入验证码");
            return;
        }
        final String phone =  register_tel.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号码");
            return;
        }
        final String password =  register_password.getText().toString();
        if(TextUtils.isEmpty(password)){
            showToast("请输入密码");
            return;
        }
        //通过verifySmsCode方式可验证该短信验证码：
        BmobSMS.verifySmsCode(phone, sms_code, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){//短信验证码已验证成功
                    Log.i("smile", "验证通过");
                    userRegister(phone,password);
                }else{
                    Log.i("smile", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                }
            }

        });
    }

    private void userRegister(String phone, String password) {
        User user = new User();
        user.setUsername(phone);
        user.setMobilePhoneNumber(phone);
        user.setMobilePhoneNumberVerified(true);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    showToast("注册成功");
                }else{
                    showToast("注册失败"+e);
                }
            }
        });
//        user.signUp(new SaveListener() {
//
//            @Override
//            public void done(Object o, BmobException e) {
//                if(e==null){
//                    showToast("注册成功");
//                }else{
//                    showToast("注册失败"+e);
//                }
//            }
//        });
        finish();
    }
}
