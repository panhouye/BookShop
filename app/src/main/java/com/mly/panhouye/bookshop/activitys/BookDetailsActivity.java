package com.mly.panhouye.bookshop.activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mly.panhouye.bookshop.Constant;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.fragments.BookAuthorDescriptionFragment;
import com.mly.panhouye.bookshop.fragments.BookCatalogFragment;
import com.mly.panhouye.bookshop.fragments.BookContentDescriptionFragment;
import com.mly.panhouye.bookshop.fragments.BookPublishingFragment;
import com.mly.panhouye.bookshop.vo.BookInfo;
import com.mly.panhouye.bookshop.vo.Comment;
import com.mly.panhouye.bookshop.vo.Orders;
import com.mly.panhouye.bookshop.vo.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BookDetailsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "BookDetailActivity";
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TextView textView4_discountPrice, textView5_price, textView6_discount, textView7_bookName,
            textView8_author;
    private SimpleDraweeView imageView_book_detail;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private BookInfo bookInfo;
    private Button button_Shopping_Cart;
    private ImageView imageView_shopping_cart;
    private TextView textView_back;

    //评价相关的组件
    private LinearLayout linearLayout4_comment;
    private TextView textView10_comment_count;
    private ImageView imageView_userIcon;
    private TextView textView11_username;
    private TextView textView12_createdAt;
    private RatingBar ratingBar_star;
    private TextView textView13_comment_content;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        bookInfo = (BookInfo) getIntent().getSerializableExtra("bookInfo");
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        initFragment();
        pager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(null);
        tabs.setViewPager(pager);
//        tabs.setIndicatorColor(0xfff6f6f6);//指示器
        tabs.setTextColor(0xFF3E3E39);//文本
//        tabs.setBackgroundColor(0xff489cfa);//背景
        tabs.setTypeface(Typeface.DEFAULT, 0);//字体类型


        pager.setCurrentItem(0);

        initView();
    }

    private void initFragment() {
        //BookContentDescriptionFragment,BookAuthorDescriptionFragment,BookCatalogFragment
        //三个都是HTML的内容
        fragments.add(BookContentDescriptionFragment.newInstance(bookInfo.getContentDescription()));
        fragments.add(BookAuthorDescriptionFragment.newInstance(bookInfo.getAuthorDescription()));
        fragments.add(BookCatalogFragment.newInstance(bookInfo.getCatalog()));
        fragments.add(BookPublishingFragment.newInstance(bookInfo));
    }

    private void initView() {
        textView4_discountPrice = (TextView) findViewById(R.id.textView4_discountPrice);
        textView5_price = (TextView) findViewById(R.id.textView5_price);
        textView6_discount = (TextView) findViewById(R.id.textView6_discount);
        textView7_bookName = (TextView) findViewById(R.id.textView7_bookName);
        textView8_author = (TextView) findViewById(R.id.textView8_author);
        imageView_book_detail = (SimpleDraweeView) findViewById(R.id.imageView_book_detail);
        button_Shopping_Cart = (Button) findViewById(R.id.button_Shopping_Cart);
        imageView_shopping_cart = (ImageView) findViewById(R.id.imageView_shopping_cart);
        button_Shopping_Cart.setOnClickListener(this);
        imageView_shopping_cart.setOnClickListener(this);
        textView_back= (TextView) findViewById(R.id.textView_back);

        //评论相关组件
        linearLayout4_comment = (LinearLayout) findViewById(R.id.linearLayout4_comment);
        linearLayout4_comment.setOnClickListener(this);
        textView10_comment_count = (TextView) findViewById(R.id.textView10_comment_count);
        imageView_userIcon = (ImageView) findViewById(R.id.user_icon);
        textView11_username = (TextView) findViewById(R.id.textView11_username);
        textView12_createdAt = (TextView) findViewById(R.id.textView12_createdAt);
        ratingBar_star = (RatingBar) findViewById(R.id.ratingBar_star);
        textView13_comment_content = (TextView) findViewById(R.id.textView13_comment_content);


        initBook();
        initComment();
        initListener();
    }

    private void initListener() {
        textView_back.setOnClickListener(this);
        imageView_shopping_cart.setOnClickListener(this);
        button_Shopping_Cart.setOnClickListener(this);
    }

    private int commentCount = 0; //评价的总数
    private Comment comment; //最新的一条评价

    private void initComment() {
        final BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("bookInfoId", bookInfo.getObjectId());
        query.order("-createdAt");
        query.count(Comment.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                commentCount = integer;
                if (commentCount > 0){
                    query.setLimit(1);
                    query.findObjects(new FindListener<Comment>() {
                        @Override
                        public void done(List<Comment> list, BmobException e) {
                            comment = list.get(0);
                            updateCommentUI();
                        }
                    });
                }
            }
        });
    }


    private void updateCommentUI() {
        textView10_comment_count.setText("(共有" + commentCount + "条评论)");
        if (comment != null) {
            textView11_username.setText(comment.getUser());
            textView12_createdAt.setText(comment.getCreatedAt());
            ratingBar_star.setRating(comment.getStar());
            textView13_comment_content.setText(comment.getContent());
        }
    }

    private void initBook() {
        textView4_discountPrice.setText("￥" + bookInfo.getDiscountPrice());
        textView5_price.setText("￥" + bookInfo.getPrice());
        textView5_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textView6_discount.setText(bookInfo.getDiscount() + "折");
        textView7_bookName.setText(bookInfo.getBookName());
        textView8_author.setText(bookInfo.getAuthor());
        imageView_book_detail.setImageURI("http://file.bmob.cn/" + bookInfo.getBookImage().getUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_back:
                finish();
                break;
            case R.id.imageView_shopping_cart:
                //判断用户是否已经登录
                User user = BmobUser.getCurrentUser(User.class);
                if(user==null){
                    showToast("请登录后再查看购物车");
                    startActivity(new Intent(this,LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(this, ShopcarActivity.class);
                startActivity(intent);
                break;
            case R.id.button_Shopping_Cart:
                addGoodToCar();//调用添加商品到购物车的功能
                break;
        }

    }
    User user;
    ArrayList<Orders> orderses;
    private void addGoodToCar() {
        //判断用户是否已经登录
        user = BmobUser.getCurrentUser(User.class);
        if(user==null){
            showToast("请登录后再购买");
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }
        // 添加商品到购物车并更新
        BmobQuery<Orders> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", user.getObjectId());
        query.addWhereEqualTo("bookInfoId", bookInfo.getObjectId());
        query.addWhereEqualTo("status", Constant.ORDER_NON_PAYMENT);
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                if(e==null){
                    orderses = (ArrayList<Orders>) list;
                    //更新订单信息(订单中已存在该)
                    if(orderses.size()>0){
                        Orders orders = orderses.get(0);
                        orders.setTotal(orders.getTotal() + 1);
                        orders.setSubtotal(orders.getTotal() * orders.getDiscountPrice());
                        orders.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    showToast("加入购物车成功");
                                }else{
                                    showToast("加入购物车失败："+e);
                                }
                            }
                        });
                    }else{
                        //保存订单
                        Orders o = new Orders();
                        o.setUserId(user.getObjectId());//用户ID
                        o.setBookInfoId(bookInfo.getObjectId());//图书ID
                        o.setTotal(1);//设置购买数量为1
                        o.setStatus(Constant.ORDER_NON_PAYMENT);//设置图书状态(未付款)
                        o.setBookName(bookInfo.getBookName());//书名
                        o.setDiscountPrice(bookInfo.getDiscountPrice());//书单价
                        o.setSubtotal(o.getTotal() * o.getDiscountPrice());//订单小计金额
                        o.setBookImage(bookInfo.getBookImage().getUrl());//图书图片路径
                        o.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    showToast("加入购物车成功");
                                }else{
                                    showToast("加入购物车失败："+e);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        private String[] titles = {"内容", "作者", "目录", "出版"};

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
