package com.hx.campus.fragment.personal.user;


import static com.hx.campus.core.webview.AgentWebFragment.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.hx.campus.R;
import com.hx.campus.activity.MainActivity;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentPhotoBinding;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

@Page()
public class PhotoFragment extends BaseFragment<FragmentPhotoBinding> {
    public static final int CHOOSE_PHOTO = 1;//CHOOSE_PHOTO：是一个全局常量，用于标识这是选择图片的这个操作，便于在回调函数中使用

    public static final int STORAGE_PERMISSION = 1;//是一个全局常量，用于标识申请的是什么权限，方便在权限的回调函数中使用。
    private File file = null;//用于上传图片文件
    private String fileName = "";//获取图片名称

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentPhotoBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentPhotoBinding.inflate(inflater, container, attachToRoot);
    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.update_photo);
    }
    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        initAc();//初始化账户数据
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.chooseimg.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            } else {
                chooseImage();

            }

        });
        binding.uploadimg.setOnClickListener(v -> {
            if (file == null) {
                Utils.showResponse(Utils.getString(getContext(),R.string.no_image_selected_yet));
            } else {
                upload();//上传图片
            }

        });
    }

    private void initAc() {
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        //设置头像
        if (TextUtils.isEmpty(user.getPhoto())) {
            binding.rivHeadPic.setVisibility(View.GONE);
        } else {
            binding.rivHeadPic.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhoto()).into(binding.rivHeadPic);
        }
    }

    //选择权限后的回调函数

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {//检查是否有读取存储卡的权限，如果有则选择图片，如果没有则提示
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                XToast.error(getContext(), "你还没有申请权限");
            }
        }

    }

    //选择图片
    private void chooseImage() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开本地存储
        //CHOOSE_PHOTO：全局常量，标识

    }

    //上传图片
    private void upload() {
        fileName.replace(".jpg", "");
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        //1、创建请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//请求类型
                .addFormDataPart("upload_file", fileName, RequestBody.create(MediaType.parse("*/*"), file)) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")数据类型，这个是所有类型的意思,file就是我们之前创建的全局file，里面是创建的图片
                .build();
        //2、调用工具类上传图片以及参数
        OkhttpUtils.uploadFile(Utils.rebuildUrl("/updatePic?id=" + user.getId(), getContext()), requestBody, new Callback() {
            //请求失败回调函数
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: 异常");
                e.printStackTrace();
            }



            //请求成功响应函数
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String msg = null;
                if (response.body() != null) {
                    msg = response.body().string();
                }
                String data = JsonOperate.getValue(msg, "data");//获取数据
                //存储信息
               // Utils.doUserData(data);
                //跳转主界面
                startActivity(new Intent(getContext(), MainActivity.class));
                Utils.showResponse(Utils.getString(getContext(),R.string.modify_success));
            }
        });
    }

    //选择图片后的回调函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode：标识码
        //data：选择的图片的信息
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO) {//显示图片
            binding.rivHeadPic.setImageURI(data.getData());
//            Log.e(TAG, "虚拟路径:" + data.getData());
            String realPath = Utils.getRealPath(getContext(), data);
//            Log.e(TAG, "真实路径:" + realPath);
            String[] temp = realPath.replaceAll("\\\\", "/").split("/");
            if (temp.length > 1) {
                fileName = temp[temp.length - 1];
            }
            file = new File(realPath);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}