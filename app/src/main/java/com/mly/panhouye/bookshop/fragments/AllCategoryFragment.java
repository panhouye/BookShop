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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mly.panhouye.bookshop.MainActivity;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.activitys.BookDetailsActivity;
import com.mly.panhouye.bookshop.adapter.BookListAdapter;
import com.mly.panhouye.bookshop.vo.BookInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by panchengjia on 2017/1/15 0015.
 */

public class AllCategoryFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{
    private MainActivity mainActivity;
    private ListView listView2_category_c;
    private LinearLayout load_layout;
    private SwipeRefreshLayout swipe_refresh_layout;
    private ArrayList<BookInfo> bookInfos = new ArrayList<>();
    private BookListAdapter bookListItemAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    public static AllCategoryFragment newInstance(String categoryId) {
        Bundle args = new Bundle();
        args.putString("categoryId",categoryId);
        AllCategoryFragment fragment = new AllCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_all,null);
        listView2_category_c = (ListView) view.findViewById(R.id.listView2_category);
        listView2_category_c.setOnItemClickListener(this);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(this);
        swipe_refresh_layout.setColorSchemeColors(0xffec5157,0xfffc9630,0xfff235ad);
        load_layout = (LinearLayout) view.findViewById(R.id.load_layout);
        bookListItemAdapter = new BookListAdapter(mainActivity,bookInfos);
        listView2_category_c.setAdapter(bookListItemAdapter);

        if (bookListItemAdapter.getCount()==0) {
            swipe_refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    swipe_refresh_layout.setRefreshing(true);
                    loadData(getArguments().getString("categoryId"));
//                    swipe_refresh_layout.setEnabled(false);//如果已经加载数据则下拉刷新不可用
                }
            });
        }
        return view;
    }

    //初始化数据
    private void loadData(String categoryId) {
        if(bookInfos.size()==0){
//            load_layout.setVisibility(View.VISIBLE);
//            listView2_category_c.setVisibility(View.GONE);
            BmobQuery<BookInfo> query = new BmobQuery<>();
            //query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.addWhereEqualTo("categoryId", categoryId);
            query.findObjects(new FindListener<BookInfo>() {
                @Override
                public void done(List<BookInfo> list, BmobException e) {
                    load_layout.setVisibility(View.GONE);
                    listView2_category_c.setVisibility(View.VISIBLE);
                    bookInfos = (ArrayList<BookInfo>) list;
                    bookListItemAdapter.setBooklist(bookInfos);
                    bookListItemAdapter.notifyDataSetChanged();
                    swipe_refresh_layout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BookInfo bookInfo = bookInfos.get(position);
        Intent intent = new Intent(mainActivity, BookDetailsActivity.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);

    }

    @Override
    public void onRefresh() {
        loadData(getArguments().getString("categoryId"));
    }
}
