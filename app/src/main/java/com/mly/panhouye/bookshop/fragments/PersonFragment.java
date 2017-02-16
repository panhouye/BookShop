package com.mly.panhouye.bookshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mly.panhouye.bookshop.MainActivity;
import com.mly.panhouye.bookshop.R;
import com.mly.panhouye.bookshop.activitys.LoginActivity;
import com.mly.panhouye.bookshop.activitys.Manage_addressActivity;
import com.mly.panhouye.bookshop.vo.User;

import java.io.File;
import java.io.FileOutputStream;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by panchengjia on 2017/1/11 0011.
 */

public class PersonFragment extends Fragment implements View.OnClickListener {

    private SimpleDraweeView draweeView;
    private LinearLayout icon_camera;
    private  LinearLayout icon_local;
//    @ViewInject(R.id.textView)
//    TextView textView;


    private Button user_login;//登陆后显示其他组件
    private Button user_address;
    private TextView user_name;
    private Button user_loginout;

    private LinearLayout user_order,daifukuan,daishouhuo,daipingjia;

    //相机拍摄的头像文件(本次演示存放在SD卡根目录下)
    private static final File USER_ICONS = new File(Environment.getExternalStorageDirectory(), "user_icon.jpg");
    private static final File EDIT_ICONS = new File(Environment.getExternalStorageDirectory(), "edit_icon.jpg");
    //请求识别码(分别为本地相册、相机、图片裁剪)
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_PHOTO_CLIP = 3;
    private static final int REQUEST_LOGIN = 0X4;
    User user;
    MainActivity mainActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person,container,false);
        initView(view);
        initListener();
        return view;
    }

    public void initView(View view) {
        //登录后显示的组件
        user_login= (Button) view.findViewById(R.id.user_login);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.user_icon);
        user_address= (Button) view.findViewById(R.id.user_address);
        user_name= (TextView) view.findViewById(R.id.user_name);
        user_loginout= (Button) view.findViewById(R.id.user_loginout);
        //登录前显示但点击后跳转登录界面
        user_order= (LinearLayout) view.findViewById(R.id.user_order);
        daifukuan= (LinearLayout) view.findViewById(R.id.daifukuan);
        daishouhuo= (LinearLayout) view.findViewById(R.id.daishouhuo);
        daipingjia= (LinearLayout) view.findViewById(R.id.daipingjia);
        booleanLogin();
    }

    private void booleanLogin() {
        //判断用户是否已经登录
        user = BmobUser.getCurrentUser(User.class);
        if(user==null){
           draweeView.setVisibility(View.GONE);
            user_address.setVisibility(View.GONE);
            user_name.setVisibility(View.GONE);
            user_loginout.setVisibility(View.GONE);
            user_login.setVisibility(View.VISIBLE);

        }else{
            user_login.setVisibility(View.GONE);
            draweeView.setVisibility(View.VISIBLE);
            user_address.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.VISIBLE);
            user_loginout.setVisibility(View.VISIBLE);
////            String userIcon = "http://file.bmob.cn/"+user.getUserIcon().getUrl();
            if(user.getUserIcon()!=null){
                draweeView.setImageURI(user.getUserIcon().getUrl());
            }else{
//                draweeView.setImageIcon(Icon.createWithBitmap(BitmapFactory.decodeResource(getResources(),
//                        R.mipmap.de_default_portrait)
//                ));
                draweeView.setImageURI(String.valueOf(R.mipmap.de_default_portrait));
            }
            user_name.setText(user.getUsername());
        }
    }

    private void initListener() {
        draweeView.setOnClickListener(this);
        user_address.setOnClickListener(this);
        user_order.setOnClickListener(this);
        daifukuan.setOnClickListener(this);
        daishouhuo.setOnClickListener(this);
        daipingjia.setOnClickListener(this);
        user_loginout.setOnClickListener(this);
        user_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_icon:
                changeIconDialog();
                System.out.println("------------------");
                break;
            case R.id.icon_camera:
                //调用相机拍照的方法
                getPicFromCamera();
                dialog.dismiss();
                break;
            case R.id.icon_local:
                //调用获取本地图片的方法
                getPicFromLocal();
                dialog.dismiss();
                break;
            case R.id.user_loginout:
                user.logOut();   //清除缓存用户对象
                booleanLogin();
                break;
            case R.id.user_login:
//                startActivity(new Intent(mainActivity,LoginActivity.class));
                startActivityForResult(new Intent(mainActivity,LoginActivity.class),REQUEST_LOGIN);
                break;
            case R.id.user_address:
                startActivity(new Intent(mainActivity, Manage_addressActivity.class));
                break;
            case R.id.user_order:

                break;
            case R.id.daifukuan:

                break;
            case R.id.daipingjia:

                break;
            case R.id.daishouhuo:

                break;

        }
    }

    AlertDialog dialog;
    public void changeIconDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("修改头像");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_changeicon,null);
        icon_camera= (LinearLayout) view.findViewById(R.id.icon_camera);
        icon_local= (LinearLayout) view.findViewById(R.id.icon_local);
        builder.setView(view);
        icon_camera.setOnClickListener(this);
        icon_local.setOnClickListener(this);
        dialog = builder.show();
    }
    /**
     * 从本机相册获取图片
     */
    private void getPicFromLocal() {
        Intent intent = new Intent();
        // 获取本地相册方法一
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //获取本地相册方法二
//        intent.setAction(Intent.ACTION_PICK);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*");
        startActivityForResult(intent, CODE_PHOTO_REQUEST);
    }
    /**
     * 通过相机拍摄获取图片，
     * 并存入设置的路径中
     */
    private void getPicFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(USER_ICONS));
        startActivityForResult(intent, CODE_CAMERA_REQUEST);
    }
    /**
     * 图片裁剪
     *
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        /*outputX outputY 是裁剪图片宽高
         *这里仅仅是头像展示，不建议将值设置过高
         * 否则超过binder机制的缓存大小的1M限制
         * 报TransactionTooLargeException
         */
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_PHOTO_CLIP);
    }
    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     * 同时上传服务器
     */
    private void setImageToHeadView() {
        if (EDIT_ICONS.exists()) {
            draweeView.setImageURI(String.valueOf(EDIT_ICONS.toURI()));
            final BmobFile bmobFile = new BmobFile(EDIT_ICONS);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()-上传头像文件成功
                        user.setUserIcon(bmobFile);
                        //服务器端更新用户头像
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    mainActivity.showToast("头像设置成功:" + bmobFile.getUrl());
                                }else {

                                }
                            }
                        });
                    }else{
                        mainActivity.showToast("头像设置失败：" + e.getMessage());
                    }
                }
            });
        }
    }
    protected void savaBitmap(Intent intent){
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            try {
                FileOutputStream out = new FileOutputStream(EDIT_ICONS);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断登录
        if (requestCode==REQUEST_LOGIN&&resultCode==RESULT_OK) {
            booleanLogin();
        }

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (USER_ICONS.exists()) {
                    photoClip(Uri.fromFile(USER_ICONS));
                }
                break;
            case CODE_PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
                }
                break;
            case CODE_PHOTO_CLIP:
                if (data != null) {
                    savaBitmap(data);
                    setImageToHeadView();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
