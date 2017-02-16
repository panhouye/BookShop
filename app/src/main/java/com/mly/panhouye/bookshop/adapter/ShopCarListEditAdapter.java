package com.mly.panhouye.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mly.panhouye.bookshop.Constant;
import com.mly.panhouye.bookshop.DoubleUtils;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.activitys.ShopcarActivity;
import com.mly.panhouye.bookshop.vo.Orders;

import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by panchengjia on 2017/1/18 0018.
 */

public class ShopCarListEditAdapter extends MyBaseAdapter {
    Context context;
    ArrayList<Orders> orderses;

    public void setOrderses(ArrayList<Orders> orderses) {
        this.orderses = orderses;
    }

    public ShopCarListEditAdapter(Context context, ArrayList<Orders> orderses) {
        this.context = context;
        this.orderses = orderses;
    }

    @Override
    public int getCount() {
        if (orderses!=null){
            return orderses.size();
        }else {
            return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_shopcar_edit,null);
            viewHolder = new ViewHolder();
            viewHolder.item_shopCarEdit_bookIcon= (SimpleDraweeView) convertView.findViewById(R.id.item_shopCarEdit_bookIcon);
            viewHolder.item_shopCarEdit_bookName= (TextView) convertView.findViewById(R.id.item_shopCarEdit_bookName);
//            viewHolder.item_shopCarEdit_del= (ImageView) convertView.findViewById(R.id.item_shopCarEdit_del);
//            viewHolder.item_shopCarEdit_subtotal= (TextView) convertView.findViewById(R.id.item_shopCarEdit_subtotal);
            convertView.setTag(viewHolder);
        }
        TextView item_shopCarEdit_subtotal= (TextView) convertView.findViewById(R.id.item_shopCarEdit_subtotal);
        ImageView item_shopCarEdit_del= (ImageView) convertView.findViewById(R.id.item_shopCarEdit_del);
        viewHolder = (ViewHolder) convertView.getTag();
        Orders orders = orderses.get(position);
        item_shopCarEdit_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders o = orderses.get(position);
                o.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                orderses.remove(position);
                ((ShopcarActivity)context).setTotalAmountToUI();
                ((ShopcarActivity)context).setProductCount();
                ShopCarListEditAdapter.this.notifyDataSetChanged();
            }
        });

        viewHolder.item_shopCarEdit_bookName.setText(orders.getBookName());
        viewHolder.item_shopCarEdit_bookIcon.setImageURI(Constant.BMOBFILE_ROOT + orders.getBookImage());
        item_shopCarEdit_subtotal.setText("￥"+ DoubleUtils.format(orders.getSubtotal()));
        return convertView;
    }
    class ViewHolder{
        SimpleDraweeView item_shopCarEdit_bookIcon;
        TextView item_shopCarEdit_bookName,item_shopCarEdit_subtotal;
        ImageView item_shopCarEdit_del;
    }
}
