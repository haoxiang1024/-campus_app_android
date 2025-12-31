package com.hx.campus.fragment.navigation.content;

import static com.hx.campus.core.webview.AgentWebFragment.TAG;
import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.Found;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentAddFoundBinding;
import com.hx.campus.utils.LoadingDialog;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

@Page
public class AddFoundFragment extends BaseFragment<FragmentAddFoundBinding> {
    public static final int CHOOSE_PHOTO = 1;//CHOOSE_PHOTO：是一个全局常量，用于标识这是选择图片的这个操作，便于在回调函数中使用

    public static final int STORAGE_PERMISSION = 1;//是一个全局常量，用于标识申请的是什么权限，方便在权限的回调函数中使用。
    int id = 0;//分类id
    private File file = null;//用于上传图片文件
    private String fileName = "";//获取图片名称
    private String foundJson;//信息json
    private String foundTitleEditValue;//标题
    private String contentEditValue;//内容
    private String locationEditValue;//地点
    private String result;
    LoadingDialog loadingDialog;//加载动画
    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentAddFoundBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentAddFoundBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        initData();
    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.send_found_info);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        /**
         * 1 图片选择
         * 2 图片上传
         */
        //图片选择
        binding.chooseImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            } else {
                chooseImage();
            }
        });
        //物品信息提交
        binding.btnSubmitLost.setOnClickListener(v -> {
            showLoadingDialog();
            User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取user
            //对象构造
            Date date = new Date();//获取日期
            String state = "未认领"; //状态
            int stick = 0;//是否置顶
            Integer userId = user.getId();//获取用户id
            String phone = user.getPhone();//用户手机号
            //获取输入框的信息
            //标题
            foundTitleEditValue = binding.etLostTitle.getEditValue();
            //内容
            contentEditValue = binding.addContent.getEditValue();
            //地点
            locationEditValue = binding.etLocation.getEditValue();
            //构造对象
            Found found = new Found(foundTitleEditValue, "", date, contentEditValue, locationEditValue, phone, state, stick, id, userId);
            //对象转换json用于传输
            foundJson = JSON.toJSONString(found);
//            Log.e(TAG, "Data: " + foundJson);
            //上传图片
            if (file == null) {
                result = Utils.getString(getContext(),R.string.no_image_selected_yet);
                showResponse(result);//反馈客户端
            } else if (TextUtils.isEmpty(foundTitleEditValue.trim())) {
                result = Utils.getString(getContext(),R.string.title_not_empty);
                showResponse(result);
            } else if (TextUtils.isEmpty(contentEditValue.trim())) {
                result = Utils.getString(getContext(),R.string.content_not_empty);
                showResponse(result);
            } else if (TextUtils.isEmpty(locationEditValue.trim())) {
                result = Utils.getString(getContext(),R.string.location_not_empty);
                showResponse(result);
            } else if (binding.radioGroup.getCheckedRadioButtonId() == -1) {
                result = Utils.getString(getContext(),R.string.category_not_selected);
                showResponse(result);
            } else {
                //将图片和对象上传服务端
                upload(foundJson);
            }

        });


    }

    private void chooseImage() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开本地存储
        //CHOOSE_PHOTO：全局常量，标识
    }

    //上传图片
    private void upload(String foundJson) {
        fileName.replace(".jpg", "");
        //1、创建请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//请求类型
                .addFormDataPart("foundJson", foundJson)
                .addFormDataPart("upload_file", fileName, RequestBody.create(MediaType.parse("*/*"), file)) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")数据类型，这个是所有类型的意思,file就是我们之前创建的全局file，里面是创建的图片
                .build();
        //2、调用工具类上传图片以及参数
        OkhttpUtils.uploadFile(Utils.rebuildUrl("/upload?op=" + "招领", getContext()), requestBody, new Callback() {

            //请求失败回调函数
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 异常");
                e.printStackTrace();
            }

            //请求成功响应函数
            @Override
            public void onResponse(Call call, Response response) {
                hideLoadingDialog();
                showResponse(Utils.getString(getContext(),R.string.send_su));
            }
        });
    }

    //ui操作，提示框
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @SuppressLint("CheckResult")
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                XToast.info(getContext(), response).show();
            }
        });
    }

    private void initData() {
        //获取登录信息
        User user = Utils.getBeanFromSp(getContext(), "User", "user");
        binding.authorName.setText(user.getNickname());
        binding.phone.setText(user.getPhone());
        //获取分类数据并设置多选按钮
        String[] types = getResources().getStringArray(R.array.type_titles);//根据app语言获取分类数据
        setRadioBtn(Arrays.asList(types));
        //获取标题id
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int checkedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(checkedRadioButtonId);
            String name = String.valueOf(radioButton.getText());
            getIdByName(name);
        });
    }


    //获取分类id
    private void getIdByName(String name) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/getTypeid?name=" + name, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String data = JsonOperate.getValue(result, "data");
                        id = Integer.parseInt(data);
                        Log.e(TAG, "onResponse: " + data);
                    }
                });
            }
        }.start();
    }

    //设置多选按钮
    private void setRadioBtn(List<String> list) {
        Log.e(TAG, "setRadioBtn: " + list);
        //找到btngroup并动态加入按钮
        for (int i = 0; i < list.size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(list.get(i));
            radioButton.setId(i);//设置唯一id以便之后获取btn内容
            binding.radioGroup.addView(radioButton);
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

    //选择图片后的回调函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode：标识码
        //data：选择的图片的信息
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO) {//显示图片
            binding.ivImage.setImageURI(data.getData());
            Log.e(TAG, "图片在手机上的虚拟路径为:" + data.getData());
            String realPath = Utils.getRealPath(getContext(), data);
            Log.e(TAG, "图片在手机上的真实路径为:" + realPath);
            String[] temp = realPath.replaceAll("\\\\", "/").split("/");
            if (temp.length > 1) {
                fileName = temp[temp.length - 1];
            }
            Log.e(TAG, "onActivityResult: " + fileName);
            file = new File(realPath);
        }
    }
    // 显示加载动画
    private void showLoadingDialog() {
        if (loadingDialog == null) {
            Context context = getContext();
            loadingDialog = new LoadingDialog(context);
        }
        loadingDialog.show();
    }

    // 隐藏加载动画
    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
