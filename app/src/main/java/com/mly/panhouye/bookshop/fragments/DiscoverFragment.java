package com.mly.panhouye.bookshop.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.vo.Category;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by panchengjia on 2017/1/11 0011.
 */

public class DiscoverFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DiscoverFragment";
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private LinearLayout ll_search_btn_container;
    private TextView tv_search_show;
    private LinearLayout load_layout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ll_search_btn_container = (LinearLayout) view.findViewById(R.id.ll_search_btn_container);
        ll_search_btn_container.setOnClickListener(this);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        load_layout = (LinearLayout) view.findViewById(R.id.load_layout);

        initData();
//        fragments.add(new JavaCategoryFragment());
//        fragments.add(new CCategoryFragment());
//        fragments.add(new AndroidCategoryFragment());
//        fragments.add(new IOSCategoryFragment());

//        pagerSlidingTabStrip.setIndicatorColor(0xfff6f6f6);//指示器
//        pagerSlidingTabStrip.setIndicatorHeight(1);
        pagerSlidingTabStrip.setTextColor(0xFFF4F6F6);//文本
//        pagerSlidingTabStrip.setBackgroundColor(0xff489cfa);//背景
        pagerSlidingTabStrip.setTypeface(Typeface.DEFAULT, 0);//字体类型
    }

    private void initData() {
        load_layout.setVisibility(View.VISIBLE);
        BmobQuery<Category> query = new BmobQuery<>();
        query.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> list, BmobException e) {
                initFragment(list);
                load_layout.setVisibility(View.GONE);
            }
        });
    }
    private void initFragment(List<Category> list) {
        if(list!=null){
            myPagerAdapter = new MyPagerAdapter(getFragmentManager(),list);
            viewPager.setAdapter(myPagerAdapter);
            viewPager.setCurrentItem(0);
            pagerSlidingTabStrip.setViewPager(viewPager);
        }
        load_layout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {

    }
    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Category> categroys;
        //private String[] titles = {"Java","C/OC/C++","Android","iOS"};
        public MyPagerAdapter(FragmentManager fm, List<Category> categroys) {
            super(fm);
            this.categroys = categroys;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categroys.get(position).getCategoryName();
        }

        @Override
        public Fragment getItem(int position) {
            return AllCategoryFragment.newInstance(categroys.get(position).getObjectId());
        }

        @Override
        public int getCount() {
            return categroys.size();
        }
    }
}
