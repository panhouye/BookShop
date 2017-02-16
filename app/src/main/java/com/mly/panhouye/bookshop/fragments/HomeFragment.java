package com.mly.panhouye.bookshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mly.panhouye.bookshop.MainActivity;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.activitys.BookDetailsActivity;
import com.mly.panhouye.bookshop.activitys.LoginActivity;
import com.mly.panhouye.bookshop.activitys.ShopcarActivity;
import com.mly.panhouye.bookshop.adapter.BookListAdapter;
import com.mly.panhouye.bookshop.vo.BookInfo;
import com.mly.panhouye.bookshop.vo.User;
import com.panxw.android.imageindicator.AutoPlayManager;
import com.panxw.android.imageindicator.ImageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by panchengjia on 2017/1/11 0011.
 */

public class HomeFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    ImageView home_shopcar;
    ImageIndicatorView indicate_view;
    ListView book_list;
    ArrayList<BookInfo> booklist;
    MainActivity mainActivity;
    BookListAdapter bookListAdapter;
    SwipeRefreshLayout swipe_refresh_layout;
    LinearLayout loading_data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        loading_data = (LinearLayout) view.findViewById(R.id.loading_data);

        home_shopcar= (ImageView) view.findViewById(R.id.home_shopcar);
        home_shopcar.setOnClickListener(this);
        indicate_view = (ImageIndicatorView) view.findViewById(R.id.indicate_view);
        book_list= (ListView) view.findViewById(R.id.book_list);
        book_list.setOnItemClickListener(this);
        booklist= new ArrayList<>();
        bookListAdapter=new BookListAdapter(mainActivity,booklist);
        book_list.setAdapter(bookListAdapter);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        swipe_refresh_layout.setOnRefreshListener(this);
        //设置三种颜色进度条样式
        swipe_refresh_layout.setColorSchemeColors(0xffec5157,0xfffc9630,0xfff235ad);
        if(bookListAdapter.getCount()==0){
            swipe_refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    swipe_refresh_layout.setRefreshing(true);
                    loadData();//booklist从bmob云服务器加载数据
                    swipe_refresh_layout.setEnabled(false);//如果已经加载数据则下拉刷新不可用
                }
            });
        }else{
            swipe_refresh_layout.setRefreshing(false);
        }

        local();//轮播图加载本地数据
        return view;
    }
    private void loadData(){
        //如果booklist中没有数据，则显示加载视图，隐藏（去除）book_list
        if(booklist.size()==0) {
            loading_data.setVisibility(View.VISIBLE);
            book_list.setVisibility(View.GONE);
            //使用云的查询方法
            BmobQuery<BookInfo> query = new BmobQuery<>();
            query.addWhereEqualTo("star", 5);//查询推荐为5星的图书
            query.findObjects(new FindListener<BookInfo>() {
                @Override
                public void done(List<BookInfo> list, BmobException e) {
                    booklist= (ArrayList<BookInfo>) list;
                    bookListAdapter.setBooklist(booklist);
                    bookListAdapter.notifyDataSetChanged();
                    loading_data.setVisibility(View.GONE);
                    book_list.setVisibility(View.VISIBLE);
                    swipe_refresh_layout.setRefreshing(false);
                }
            });
        }
    }


    private void local() {
        // 声明一个数组, 指定图片的ID
        final Integer[] resArray = new Integer[] {R.mipmap.qdzt, R.mipmap.qinghuabiancheng,
                R.mipmap.scala};
        // 把数组交给图片展播组件
        indicate_view.setupLayoutByDrawable(resArray);
        // 展播的风格
//        indicate_view.setIndicateStyle(ImageIndicatorView.INDICATE_ARROW_ROUND_STYLE);
        indicate_view.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);
        // 显示组件
        indicate_view.show();
        final AutoPlayManager autoBrocastManager = new AutoPlayManager(indicate_view);
        //设置开启自动广播
        autoBrocastManager.setBroadcastEnable(true);
        //autoBrocastManager.setBroadCastTimes(5);//loop times
        //设置开始时间和间隔时间
        autoBrocastManager.setBroadcastTimeIntevel(2000, 2000);
        //设置循环播放
        autoBrocastManager.loop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_shopcar:
                //判断用户是否已经登录
                User user = BmobUser.getCurrentUser(User.class);
                if(user==null){
                    mainActivity.showToast("请登录后再查看购物车");
                    startActivity(new Intent(mainActivity,LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(mainActivity, ShopcarActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BookInfo bookInfo = booklist.get(position);
        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);
    }
}
