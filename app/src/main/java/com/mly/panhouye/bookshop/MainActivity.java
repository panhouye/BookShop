package com.mly.panhouye.bookshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mly.panhouye.bookshop.activitys.BaseActivity;
import com.mly.panhouye.bookshop.adapter.MyFragmentPagerAdapter;
import com.mly.panhouye.bookshop.fragments.DiscoverFragment;
import com.mly.panhouye.bookshop.fragments.HomeFragment;
import com.mly.panhouye.bookshop.fragments.PersonFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    //声明存储fragment的集合
    private ArrayList<Fragment> fragments;
    //声明导航对应fragment
    HomeFragment homeFragment;
    DiscoverFragment discoverFragment;
    PersonFragment personFragment;
    //声明ViewPager
    private ViewPager viewPager;
    FragmentManager fragmentManager;//声明fragment管理
    //声明导航栏中对应的布局
    private LinearLayout home, discover, person;
    //声明导航栏中包含的imageview和textview
    private ImageView home_img, discover_img, person_img;
    private TextView home_txt, discover_txt, person_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化加载首页布局
        initView();
        //调用自定义initListener方法，为各个组件添加监听事件
        initListener();
        //设置默认选择的pager和导航栏的状态
        viewPager.setCurrentItem(0);
        home_img.setSelected(true);
        home_txt.setSelected(true);
    }
    private void initListener() {
        //导航组件添加监听
        home.setOnClickListener(this);
        discover.setOnClickListener(this);
        person.setOnClickListener(this);

        //为viewpager添加页面变化的监听以及事件处理
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据位置直接决定显示哪个fragment
                viewPager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        home_img.setSelected(true);
                        home_txt.setSelected(true);

                        discover_img.setSelected(false);
                        discover_txt.setSelected(false);
                        person_img.setSelected(false);
                        person_txt.setSelected(false);

                        break;
                    case 1:
                        discover_img.setSelected(true);
                        discover_txt.setSelected(true);

                        home_img.setSelected(false);
                        home_txt.setSelected(false);
                        person_img.setSelected(false);
                        person_txt.setSelected(false);
                        break;
                    case 2:
                        person_img.setSelected(true);
                        person_txt.setSelected(true);

                        home_img.setSelected(false);
                        home_txt.setSelected(false);
                        discover_img.setSelected(false);
                        discover_txt.setSelected(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void initView() {
        //在主布局中根据id找到ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //实例化所属fragment
//        homeFragment = new HomeFragment();
//        discoverFragment = new DiscoverFragment();
//        personFragment = new PersonFragment();
//        fragments = new ArrayList<>();
        homeFragment=new HomeFragment();
        discoverFragment=new DiscoverFragment();
        personFragment=new PersonFragment();
        fragments = new ArrayList<>();
        //添加fragments到集合中
        fragments.add(homeFragment);
        fragments.add(discoverFragment);
        fragments.add(personFragment);
        fragmentManager = getSupportFragmentManager();
        //为ViewPager设置适配器用于部署fragments
        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager,fragments));

        home= (LinearLayout) findViewById(R.id.home);
        discover= (LinearLayout) findViewById(R.id.discover);
        person = (LinearLayout) findViewById(R.id.person);


        home_img = (ImageView) findViewById(R.id.home_img);
        discover_img = (ImageView) findViewById(R.id.dicover_img);
        person_img = (ImageView) findViewById(R.id.person_img);

        home_txt = (TextView) findViewById(R.id.home_txt);
        discover_txt = (TextView) findViewById(R.id.dicover_txt);
        person_txt = (TextView) findViewById(R.id.person_txt);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                viewPager.setCurrentItem(0);
                home_img.setSelected(true);
                home_txt.setSelected(true);

                discover_img.setSelected(false);
                discover_txt.setSelected(false);
                person_img.setSelected(false);
                person_txt.setSelected(false);

                break;
            case R.id.discover:
                viewPager.setCurrentItem(1);
                discover_img.setSelected(true);
                discover_txt.setSelected(true);

                home_img.setSelected(false);
                home_txt.setSelected(false);
                person_img.setSelected(false);
                person_txt.setSelected(false);

                break;
            case R.id.person:
                viewPager.setCurrentItem(2);
                person_img.setSelected(true);
                person_txt.setSelected(true);

                home_img.setSelected(false);
                home_txt.setSelected(false);
                discover_img.setSelected(false);
                discover_txt.setSelected(false);
                break;

        }
    }
}
