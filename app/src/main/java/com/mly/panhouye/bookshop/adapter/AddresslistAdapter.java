package com.mly.panhouye.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.Address;

import java.util.ArrayList;

import static com.mly.panhouye.bookshop.R.id.address;

/**
 * Created by panchengjia on 2017/1/16 0016.
 */

public class AddresslistAdapter extends MyBaseAdapter {
    ArrayList<Address> addresses;
    Context context;

    public AddresslistAdapter(ArrayList<Address> addresses, Context context) {
        this.addresses = addresses;
        this.context = context;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public int getCount() {
        if(addresses==null){
            return 0;
        }else{
            return addresses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_address,null);
            viewHolder=new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tel = (TextView) convertView.findViewById(R.id.tel);
            viewHolder.address= (TextView) convertView.findViewById(address);
            viewHolder.address_defaut= (TextView) convertView.findViewById(R.id.address_defaut);
            convertView.setTag(viewHolder);
        }
        viewHolder=new ViewHolder();
        viewHolder.name.setText(addresses.get(position).getUsername());
        viewHolder.tel.setText(addresses.get(position).getPhoneNumber());
        viewHolder.address.setText(addresses.get(position).getAddress());
        if (addresses.get(position).getIsDefault()){
            viewHolder.address_defaut.setVisibility(View.VISIBLE);
        }else{
            viewHolder.address_defaut.setVisibility(View.GONE);
        }

        return convertView;
    }
    class ViewHolder{
        TextView name,tel,address_defaut,address;
    }
}
