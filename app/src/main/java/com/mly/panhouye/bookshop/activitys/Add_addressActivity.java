package com.mly.panhouye.bookshop.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.Address;
import com.mly.panhouye.bookshop.vo.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class Add_addressActivity extends BaseActivity implements View.OnClickListener {
    TextView add_address_name,add_address_tel,add_address_address,add_address_back;
    Button add_address_save,add_address_default;
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        iniListner();
    }

    private void iniListner() {
        add_address_save.setOnClickListener(this);
        add_address_default.setOnClickListener(this);
        add_address_back.setOnClickListener(this);
    }

    private void initView() {
        add_address_back = (TextView) findViewById(R.id.add_address_back);
        add_address_name = (TextView) findViewById(R.id.add_address_name);
        add_address_tel = (TextView) findViewById(R.id.add_address_tel);
        add_address_address = (TextView) findViewById(R.id.add_address_address);
        add_address_save = (Button) findViewById(R.id.add_address_save);
        add_address_default = (Button) findViewById(R.id.add_address_default);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_address_save:
                createAddress(false);
                break;
            case R.id.add_address_default:
                saveAndSetDefault();
                break;
            case R.id.add_address_back:
                finish();
                break;
        }

    }
    private void createAddress(boolean isDefault) {
        String username = add_address_name.getText().toString();
        if (TextUtils.isEmpty(username)){
            showToast("请输入收货人姓名");
            return;
        }
        String phoneNumber = add_address_tel.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)){
            showToast("请输入收货人手机号码");
            return;
        }
        String addressInfo = add_address_address.getText().toString();
        if (TextUtils.isEmpty(addressInfo)){
            showToast("请输入收货人详细地址");
            return;
        }
        address = new Address();
        address.setUsername(username);
        address.setPhoneNumber(phoneNumber);
        address.setAddress(addressInfo);
        address.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
        address.setIsDefault(isDefault);
        address.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    showToast("保存地址成功");
                    finish();
                }else {
                    showToast("保存地址失败:"+e);
                }
            }
        });
    }
    /**
     * 保存地址及设置为默认
     */
    private void saveAndSetDefault() {
        createAddress(true);
        //修改默认地址为非默认
        BmobQuery<Address> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser(User.class).getObjectId());
        query.addWhereEqualTo("isDefault",true);
        query.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> list, BmobException e) {
                if(e==null){
                    Address temp;
                    for (int i=0;i<list.size();i++){
                    temp = list.get(i);
                    temp.setIsDefault(false);
                    temp.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
                    }
                }
            }
        });
    }
}
