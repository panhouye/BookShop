package com.mly.panhouye.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mly.panhouye.bookshop.Constant;
import com.mly.panhouye.bookshop.DoubleUtils;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.Orders;

import java.util.ArrayList;

/**
 * Created by panchengjia on 2017/1/18 0018.
 */

public class UserPaylistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Orders> orderses;

    public UserPaylistAdapter(Context context, ArrayList<Orders> orderses) {
        this.context = context;
        this.orderses = orderses;
    }

    public void setOrderses(ArrayList<Orders> orderses) {
        this.orderses = orderses;
    }

    @Override
    public int getCount() {
        if(orderses==null){
            return 0;
        }else{
            return orderses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return orderses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_userpay,null);
            viewHolder = new ViewHolder();
            viewHolder.item_userpay_bookcon = (SimpleDraweeView) convertView.findViewById(R.id.item_userpay_bookcon);
            viewHolder.item_userpay_bookcount= (TextView) convertView.findViewById(R.id.item_userpay_bookcount);
            viewHolder.item_userpay_bookname=(TextView) convertView.findViewById(R.id.item_userpay_bookname);
            viewHolder.item_userpay_bookprice=(TextView) convertView.findViewById(R.id.item_userpay_bookprice);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Orders orders = orderses.get(position);
        viewHolder.item_userpay_bookname.setText(orders.getBookName());
        viewHolder.item_userpay_bookprice.setText(DoubleUtils.format(orders.getSubtotal())+"元");
        viewHolder.item_userpay_bookcon.setImageURI(Constant.BMOBFILE_ROOT + orders.getBookImage());
        viewHolder.item_userpay_bookcount.setText(orders.getTotal()+"");
        return convertView;
    }
    class ViewHolder{
        SimpleDraweeView item_userpay_bookcon;
        TextView item_userpay_bookname,item_userpay_bookcount,item_userpay_bookprice;
    }
}
