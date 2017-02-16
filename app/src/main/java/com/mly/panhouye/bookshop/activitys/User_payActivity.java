package com.mly.panhouye.bookshop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mly.panhouye.bookshop.DoubleUtils;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.adapter.UserPaylistAdapter;
import com.mly.panhouye.bookshop.vo.Orders;

import java.util.ArrayList;


public class User_payActivity extends AppCompatActivity implements View.OnClickListener{
    ListView userpay_orders;
    TextView userpay_subtotal,userpay_back;
    Button userpay_ok;
    ArrayList<Orders> orderlist;
    UserPaylistAdapter adapter;
    Double totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pay);
        Intent intent = getIntent();
        totalAmount = intent.getDoubleExtra("totalAmount",0); //订单总金额

        orderlist= (ArrayList<Orders>) intent.getSerializableExtra("orderList");
        initView();
        initListenner();
        adapter = new UserPaylistAdapter(this,orderlist);
        userpay_orders.setAdapter(adapter);
    }

    private void initListenner() {
        userpay_back.setOnClickListener(this);
    }

    private void initView() {
        userpay_orders= (ListView) findViewById(R.id.userpay_orders);
        userpay_subtotal= (TextView) findViewById(R.id.userpay_subtotal);
        userpay_ok= (Button) findViewById(R.id.userpay_ok);
        userpay_back= (TextView) findViewById(R.id.userpay_back);
        userpay_subtotal.setText("总计："+DoubleUtils.format(DoubleUtils.format(totalAmount))+"元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userpay_back:
                finish();
                break;
        }
    }
}
