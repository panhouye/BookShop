package com.mly.panhouye.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by panchengjia on 2017/1/17 0017.
 */

public class ShopCarListAdapter extends MyBaseAdapter  {
    Context context;
    ArrayList<Orders> orderses;

    public void setOrderses(ArrayList<Orders> orderses) {
        this.orderses = orderses;
    }

    public ShopCarListAdapter(Context context, ArrayList<Orders> orderses) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_shopcar,null);
            viewHolder = new ViewHolder();
//            viewHolder.item_shopCar_add = (ImageView) convertView.findViewById(R.id.item_shopCar_add);
            viewHolder.item_shopCar_bookIcon= (SimpleDraweeView) convertView.findViewById(R.id.item_shopCar_bookIcon);
            viewHolder.item_shopCar_bookName= (TextView) convertView.findViewById(R.id.item_shopCar_bookName);
//            viewHolder.item_shopCar_bookTotal= (TextView) convertView.findViewById(item_shopCar_bookTotal);
//            viewHolder.item_shopCar_reduce= (ImageView) convertView.findViewById(R.id.item_shopCar_reduce);
//            viewHolder.item_shopCar_subtotal= (TextView) convertView.findViewById(R.id.item_shopCar_subtotal);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        final Orders currentOrder = orderses.get(position);

        final TextView item_shopCar_bookTotal = (TextView) convertView.findViewById(R.id.item_shopCar_bookTotal);
        final TextView item_shopCar_subtotal= (TextView) convertView.findViewById(R.id.item_shopCar_subtotal);
        ImageView item_shopCar_add = (ImageView) convertView.findViewById(R.id.item_shopCar_add);
        ImageView item_shopCar_reduce = (ImageView) convertView.findViewById(R.id.item_shopCar_reduce);
        item_shopCar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total=Integer.parseInt(item_shopCar_bookTotal.getText().toString());
                total++;
                if (total>100){//建议此处获取图书库存再作判断
                    total=100;
                }
                currentOrder.setTotal(total);
                currentOrder.setSubtotal(total*currentOrder.getDiscountPrice());
                item_shopCar_bookTotal.setText(total+"");
//                item_shopCar_subtotal.setText("￥"+ DoubleUtils.format(currentOrder.getDiscountPrice()*total));
                item_shopCar_subtotal.setText("￥"+ DoubleUtils.format(currentOrder.getSubtotal()));
                currentOrder.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ((ShopcarActivity)context).setTotalAmountToUI();
            }
        });
        item_shopCar_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total=Integer.parseInt(item_shopCar_bookTotal.getText().toString());
                total--;
                if (total<1){//建议此处获取图书库存再作判断
                    total=1;
                }
                currentOrder.setTotal(total);
                currentOrder.setSubtotal(total*currentOrder.getDiscountPrice());
                item_shopCar_bookTotal.setText(total+"");
//                item_shopCar_subtotal.setText("￥"+ DoubleUtils.format(currentOrder.getDiscountPrice()*total));
                item_shopCar_subtotal.setText("￥"+ DoubleUtils.format(currentOrder.getSubtotal()));
                currentOrder.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ((ShopcarActivity)context).setTotalAmountToUI();
            }
        });
        item_shopCar_bookTotal.setText(currentOrder.getTotal()+"");
        item_shopCar_subtotal.setText("￥"+ DoubleUtils.format(currentOrder.getSubtotal()));
        viewHolder.item_shopCar_bookName.setText(currentOrder.getBookName());
        viewHolder.item_shopCar_bookIcon.setImageURI(Constant.BMOBFILE_ROOT + currentOrder.getBookImage());

        return convertView;
    }

    class ViewHolder{
        TextView item_shopCar_bookName,item_shopCar_subtotal,item_shopCar_bookTotal;
        ImageView item_shopCar_reduce,item_shopCar_add;
        SimpleDraweeView item_shopCar_bookIcon;
    }
}
