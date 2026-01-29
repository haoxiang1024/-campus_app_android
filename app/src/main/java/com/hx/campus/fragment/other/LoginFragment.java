package com.hx.campus.fragment.other;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.hx.campus.R;
import com.hx.campus.activity.MainActivity;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentLoginBinding;
import com.hx.campus.utils.LoadingDialog;
import com.hx.campus.utils.RandomUtils;
import com.hx.campus.utils.ResponseMsg;
import com.hx.campus.utils.SettingUtils;
import com.hx.campus.utils.TokenUtils;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.api.ApiService;
import com.hx.campus.utils.api.Result;
import com.hx.campus.utils.api.RetrofitClient;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.sdkinit.UMengInit;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import retrofit2.Callback;


@Page(anim = CoreAnim.none)
public class LoginFragment extends BaseFragment<FragmentLoginBinding> implements View.OnClickListener {




    String loginMsg = "";//登录信息

    LoadingDialog loadingDialog;//加载动画




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //初始化控件
    @Override
    protected void initViews() {

        //隐私政策弹窗
        if (!SettingUtils.isAgreePrivacy()) {
            Utils.showPrivacyDialog(getContext(), (dialog, which) -> {
                dialog.dismiss();
                handleSubmitPrivacy();
            });
        }
        boolean isAgreePrivacy = SettingUtils.isAgreePrivacy();
        binding.cbProtocol.setChecked(isAgreePrivacy);
        refreshButton(isAgreePrivacy);
        binding.cbProtocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingUtils.setIsAgreePrivacy(isChecked);
            refreshButton(isChecked);
        });
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        //titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close));
        titleBar.setActionTextColor(ThemeUtils.resolveColor(getContext(), R.attr.colorAccent));
        return titleBar;
    }

    //初始化监听器
    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.tvUserProtocol.setOnClickListener(this);
        binding.tvPrivacyProtocol.setOnClickListener(this);
    }

    @NonNull
    @Override
    protected FragmentLoginBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentLoginBinding.inflate(inflater, container, attachToRoot);

    }


    private void refreshButton(boolean isChecked) {
        ViewUtils.setEnabled(binding.btnLogin, isChecked);
    }

    //提交隐私政策
    private void handleSubmitPrivacy() {
        SettingUtils.setIsAgreePrivacy(true);
        UMengInit.init();

    }

    //控件点击事件
    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        try {
            switch (id) {
                case R.id.btn_login:
                    // 登录
                    //showLoadingDialog();//显示加载动画
                    if (binding.etPhoneNumber.validate() && binding.etPassword.validate()) {
                        //校验成功进行登录
                        Login();
                    }else {
                        Utils.showResponse(ResponseMsg.ACCOUNT_PWD_ERROR);
                    }
                    break;
                case R.id.tv_user_protocol:
                    // 用户协议
                    Utils.gotoProtocol(this, false, true);
                    break;
                case R.id.tv_privacy_protocol:
                    // 隐私政策
                    Utils.gotoProtocol(this, true, true);
                    break;
                case R.id.tv_forget_password:
                    // 忘记密码
                    openNewPage(ResetPwdFragment.class);
                    break;
                default:
                    Utils.showResponse(ResponseMsg.REQUEST_FAIL);
                    break;
            }
        } catch (Exception e) {
            Utils.showResponse(ResponseMsg.FAIL);

        }

    }

    /**
     * 登录成功的处理
     */
    private void Login() {
        //登录注册的处理
        String phoneNumber = binding.etPhoneNumber.getEditValue();
        String password = binding.etPassword.getEditValue();
        login(phoneNumber, password);

    }

    private void login(String phone, String password) {
        RetrofitClient.getInstance().getApi().login(phone, password).enqueue(new retrofit2.Callback<Result<User>>() {
            @Override
            public void onResponse(retrofit2.Call<Result<User>> call, retrofit2.Response<Result<User>> response) {
                if (response.body() != null) {
                    Result<User> result = response.body();
                    if (result.isSuccess()) {
                        User user = response.body().getData();
                        Utils.doUserData(user);
                        TokenUtils.setToken(RandomUtils.getRandomLetters(6));
                        ActivityUtils.startActivity(MainActivity.class);
                    } else {
                        Utils.showResponse(result.getMsg());
                    }
                } else {
                    Utils.showResponse("服务器响应为空");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Result<User>> call, Throwable t) {
                Utils.showResponse("网络请求失败");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideLoadingDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

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
