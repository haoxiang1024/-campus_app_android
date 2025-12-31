package com.hx.campus.fragment.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mob.MobSDK;
import com.hx.campus.R;
import com.hx.campus.activity.LoginActivity;
import com.hx.campus.activity.MainActivity;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentOtherLoginBinding;
import com.hx.campus.utils.LoadingDialog;
import com.hx.campus.utils.RandomUtils;
import com.hx.campus.utils.SettingUtils;
import com.hx.campus.utils.TokenUtils;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.sdkinit.UMengInit;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

@Page
public class OtherLoginFragment extends BaseFragment<FragmentOtherLoginBinding> implements View.OnClickListener {

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.otherLogin.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.tvPrivacyProtocol.setOnClickListener(this);
        binding.tvUserProtocol.setOnClickListener(this);

    }
    LoadingDialog loadingDialog;//加载动画
    @Override
    protected TitleBar initTitle() {
        return super.initTitle();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                //网络请求密码登录
                //验证手机号,密码是否通过
                if (binding.etPhoneNumber.validate()) {
                    if (binding.etPassword.validate()) {
                        pwdLogin();
                    }

                }
                break;
            case R.id.tv_user_protocol:
                //用户协议
                Utils.gotoProtocol(this, false, true);
                break;
            case R.id.tv_privacy_protocol:
                //隐私政策
                Utils.gotoProtocol(this, true, true);
                break;
            case R.id.other_login:
                //验证码登录
                Intent intent = new Intent(getContext(), LoginActivity.class);
                popToBack();
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                //忘记密码
                openNewPage(ResetPwdFragment.class);
                break;
            default:
                break;

        }

    }

    private void pwdLogin() {
        //获取数据
        String number = binding.etPhoneNumber.getEditValue();
        String password = binding.etPassword.getEditValue();
        showLoadingDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/loginByPwd?phone=" + number + "&pwd=" + password, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String msg = JsonOperate.getValue(result, "msg");//登录信息
                        String loginMsg = JsonOperate.getValue(result, "data");//登录数据
                        if (msg.equals("登录成功")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.login_su));
                            getActivity().runOnUiThread(()->hideLoadingDialog());
                            //获取信息并存储
                            Utils.doUserData(loginMsg);
                            //设置登录token
                            TokenUtils.setToken(RandomUtils.getRandomLetters(6));
                            //跳转主界面
                            ActivityUtils.startActivity(MainActivity.class);
                        } else if (msg.equals("登录失败,请检查手机号密码是否正确")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.login_fail));
                            loadingDialog.dismiss();
                        }

                    }
                });
            }
        }.start();

    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return "密码登录";
    }

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentOtherLoginBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentOtherLoginBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        //隐私政策
        boolean isAgreePrivacy = SettingUtils.isAgreePrivacy();
        binding.cbProtocol.setChecked(isAgreePrivacy);
        refreshButton(isAgreePrivacy);
        binding.cbProtocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingUtils.setIsAgreePrivacy(isChecked);
            handleSubmitPrivacy();
            refreshButton(isChecked);
        });
    }

    private void refreshButton(boolean isChecked) {
        ViewUtils.setEnabled(binding.btnLogin, isChecked);
    }

    //提交隐私政策
    private void handleSubmitPrivacy() {
        SettingUtils.setIsAgreePrivacy(true);
        UMengInit.init();
        //mobsdk隐私政策
        MobSDK.submitPolicyGrantResult(true);
    }

    /**
     * 将Activity中onKeyDown在Fragment中实现，
     *
     * @param keyCode keyCode码
     * @param event   KeyEvent对象
     * @return 是否处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
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