package com.mly.panhouye.bookshop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mly.panhouye.bookshop.Constant;
import com.mly.panhouye.bookshop.DoubleUtils;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.adapter.ShopCarListAdapter;
import com.mly.panhouye.bookshop.adapter.ShopCarListEditAdapter;
import com.mly.panhouye.bookshop.vo.BookInfo;
import com.mly.panhouye.bookshop.vo.Orders;
import com.mly.panhouye.bookshop.vo.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShopcarActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    TextView shopcar_back,shopcar_edit,shopcar_count,shopcar_price,shopcar_pay,shopcar_finish;
    ShopCarListAdapter shopCarListAdapter;
    ShopCarListEditAdapter shopCarListEditAdapter;
    ArrayList<Orders> orderses;
    ListView shopping_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
        initView();
        initListener();
        initData();
        shopCarListEditAdapter = new ShopCarListEditAdapter(this,orderses);
        shopCarListAdapter = new ShopCarListAdapter(this,orderses);
        shopping_list.setAdapter(shopCarListAdapter);
        shopping_list.setOnItemClickListener(this);
    }

    private void initView() {
        shopping_list= (ListView) findViewById(R.id.shopping_list);
        shopcar_back= (TextView) findViewById(R.id.shopcar_back);
        shopcar_edit= (TextView) findViewById(R.id.shopcar_edit);
        shopcar_count= (TextView) findViewById(R.id.shopcar_count);
        shopcar_price= (TextView) findViewById(R.id.shopcar_price);
        shopcar_finish= (TextView) findViewById(R.id.shopcar_finish);
        shopcar_pay= (TextView) findViewById(R.id.shopcar_pay);
        shopcar_pay.setEnabled(false);
    }
    private void initListener() {
        shopcar_back.setOnClickListener(this);
        shopcar_edit.setOnClickListener(this);
        shopcar_pay.setOnClickListener(this);
        shopcar_finish.setOnClickListener(this);
    }

    private void initData() {
        BmobQuery<Orders> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("userId",user.getObjectId());
        query.addWhereEqualTo("status", Constant.ORDER_NON_PAYMENT);
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                if (e==null){
                    orderses= (ArrayList<Orders>) list;
                    shopCarListAdapter.setOrderses(orderses);
                    shopCarListAdapter.notifyDataSetChanged();
                    shopCarListEditAdapter.setOrderses(orderses);
                    shopCarListEditAdapter.notifyDataSetChanged();
                    setProductCount();
                    setTotalAmountToUI();
                }
            }
        });
    }
    //设置总金额到UI组件
    public void setTotalAmountToUI(){
        double totalAmount = getTotalAmount();
        shopcar_price.setText("金额:"+ DoubleUtils.format(totalAmount)+"元");
        if (totalAmount<=0){
            shopcar_pay.setEnabled(false);
        }else{
            shopcar_pay.setEnabled(true);
        }
    }

    //设置叫商品数
    public void setProductCount(){
        shopcar_count.setText("共"+orderses.size()+"件商品");
    }

    //计算总金额
    private double getTotalAmount() {
        double totalAmount = 0;
        Orders orders;
        for (int i=0;i<orderses.size();i++){
            orders = orderses.get(i);
            totalAmount+=orders.getSubtotal();
        }
        return totalAmount;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopcar_back:
                finish();
                break;
            case  R.id.shopcar_edit:
                shopcar_edit.setVisibility(View.GONE);
                shopcar_finish.setVisibility(View.VISIBLE);
                shopping_list.setAdapter(shopCarListEditAdapter);
                break;
            case R.id.shopcar_finish:
                shopcar_edit.setVisibility(View.VISIBLE);
                shopcar_finish.setVisibility(View.GONE);
                shopping_list.setAdapter(shopCarListAdapter);
                break;
            case  R.id.shopcar_pay:
                Intent intent = new Intent(this,User_payActivity.class);
                intent.putExtra("totalAmount",getTotalAmount());
                intent.putExtra("orderList",orderses);
                startActivity(intent);
                break;
        }

    }
        BookInfo book;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Orders o = orderses.get(position);
        BmobQuery<BookInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("bookName", o.getBookName());//查询图书
        query.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                book=list.get(0);
                Intent intent = new Intent(ShopcarActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookInfo",book);
                startActivity(intent);
            }
        });
    }
}
