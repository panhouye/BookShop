package com.mly.panhouye.bookshop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.BookInfo;

import java.util.ArrayList;

/**
 * Created by panchengjia on 2017/1/13 0013.
 */

public class BookListAdapter extends MyBaseAdapter {
    Context context;
    ArrayList<BookInfo> booklist;
    public ArrayList<BookInfo> getBooklist() {
        return booklist;
    }

    public void setBooklist(ArrayList<BookInfo> booklist) {
        this.booklist = booklist;
    }

    public BookListAdapter(Context context, ArrayList<BookInfo> booklist) {
        this.context = context;
        this.booklist = booklist;
    }

    @Override
    public int getCount() {
        if (booklist!=null){
            return booklist.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return booklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_homebooklist,null);
            viewHolder = new ViewHolder();
            viewHolder.book_discount = (TextView) convertView.findViewById(R.id.book_discount);
            viewHolder.book_discountprice = (TextView) convertView.findViewById(R.id.book_discountprice);
            viewHolder.book_price = (TextView) convertView.findViewById(R.id.book_price);
            viewHolder.book_start = (TextView) convertView.findViewById(R.id.book_start);
            viewHolder.book_name= (TextView) convertView.findViewById(R.id.book_name);
            viewHolder.book_icon= (SimpleDraweeView) convertView.findViewById(R.id.book_icon);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.book_icon.setImageURI("http://file.bmob.cn/"+booklist.get(position).getBookImage().getUrl());
        viewHolder.book_discount.setText( booklist.get(position).getDiscount()+"折");
        viewHolder.book_name.setText(booklist.get(position).getBookName());
        viewHolder.book_start.setText(booklist.get(position).getStar()+"星");
        viewHolder.book_discountprice.setText("￥"+booklist.get(position).getDiscountPrice()+"");
        viewHolder.book_price.setText("原价：￥"+booklist.get(position).getPrice()+"");
        viewHolder.book_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//给文本内容设置删除线样式
        return convertView;
    }
    class ViewHolder {
        SimpleDraweeView book_icon;
        TextView book_name,book_discountprice,book_price,book_discount,book_start;
    }
}


