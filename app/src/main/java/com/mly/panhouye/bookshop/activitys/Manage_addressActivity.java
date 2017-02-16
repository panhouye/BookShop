package com.mly.panhouye.bookshop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.adapter.AddresslistAdapter;
import com.mly.panhouye.bookshop.vo.Address;
import com.mly.panhouye.bookshop.vo.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Manage_addressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //    @ViewInject(R.id.manage_address_back)
    TextView manage_address_back;
    //    @ViewInject(R.id.manage_address_add)
    ImageView manage_address_add;
    //    @ViewInject(R.id.manage_addresslist)
    ListView manage_address_list;

    ArrayList<Address> addresses ;
    AddresslistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        manage_address_back = (TextView) findViewById(R.id.manage_address_back);
        manage_address_add = (ImageView) findViewById(R.id.manage_address_add);
        manage_address_list = (ListView) findViewById(R.id.manage_addresslist);
        initListener();
        addresses = new ArrayList<>();
        initDate();
        adapter = new AddresslistAdapter(addresses, this);
        manage_address_list.setAdapter(adapter);
    }

    private void initDate() {
//        addresses = (ArrayList<Address>) getIntent().getSerializableExtra("addresses");
        if (addresses == null) {
            BmobQuery<Address> query = new BmobQuery<>();
            query.addWhereEqualTo("userId", BmobUser.getCurrentUser(User.class).getObjectId());
            query.order("-isDefault");//排序isDefault降序
            query.findObjects(new FindListener<Address>() {
                @Override
                public void done(List<Address> list, BmobException e) {
                    if (e == null) {
                        addresses = (ArrayList<Address>) list;
                        adapter.setAddresses(addresses);
                        adapter.notifyDataSetChanged();
                        showToast("chazhao");
                    } else {
                        showToast("查找地址失败");
                    }
                }
            });
        }
    }

    private void initListener() {
        manage_address_list.setOnItemClickListener(this);
        manage_address_add.setOnClickListener(this);
        manage_address_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_address_add:
                startActivity(new Intent(this, Add_addressActivity.class));
                break;
            case R.id.manage_address_back:
                finish();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Edit_addressActivity.class);
        intent.putExtra("address", addresses.get(position));
        startActivity(intent);
        finish();
    }
}
