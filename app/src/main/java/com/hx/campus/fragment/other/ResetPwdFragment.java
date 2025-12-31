package com.hx.campus.fragment.other;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.hx.campus.R;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentResetPwdBinding;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;


@Page
public class ResetPwdFragment extends BaseFragment<FragmentResetPwdBinding> implements View.OnClickListener {



    private CountDownButtonHelper mCountDownHelper;
    private boolean vlidate = false;//验证码是否校验成功


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentResetPwdBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentResetPwdBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mCountDownHelper = new CountDownButtonHelper(binding.btnGetVerifyCode, 60);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return "重置密码";
    }

    @Override
    protected void initListeners() {
        binding.btnReset.setOnClickListener(this);
        binding.btnGetVerifyCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_reset) {
            //重置密码
            reset();

        } else if (id==R.id.btn_get_verify_code) {
            //获取验证码
            send();
        }

    }


    private void reset() {
        //获取数据
        String number = binding.etPhoneNumber.getEditValue();
        String password = binding.etPassword.getEditValue();
        String repassword = binding.rePassword.getEditValue();
        String code = binding.inputCode.getEditValue();
        String email = binding.inputEmail.getEditValue();
        if(!password.equals(repassword)){
            Utils.showResponse( "两次密码不一致");
            return;
        }
        //验证邮箱
        vlidateEmail( email,code);
        if(vlidate){
            //重置密码
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    OkhttpUtils.get(Utils.rebuildUrl("/resetPwd?phone=" + number + "&newPwd=" + password + "&email=" + email + "&email_code=" + code, getContext()), new OkHttpCallback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            super.onResponse(call, response);
                            // 1. 主线程执行UI操作和导航
                            new Handler(Looper.getMainLooper()).post(() -> {
                                // 3. 关闭当前页面
                                try {
                                    requireActivity().onBackPressed();
                                    // 2. 显示成功提示
                                    Utils.showResponse(Utils.getString(requireContext(), R.string.reset_su));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }.start();
        }else {
            //验证码校验失败
            Utils.showResponse( "验证码校验失败");
        }



    }
//验证邮箱
    private void vlidateEmail(String email,String code){
    new Thread() {
        @Override
        public void run() {
            super.run();
            OkhttpUtils.get(Utils.rebuildUrl("/verify_code?email=" + email+"&code="+code , getContext()), new OkHttpCallback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    super.onResponse(call, response);
                    if(this.result.){
                        vlidate = true;
                    }

                }
            });
        }
    }.start();
    }
    private void send(){
        //获取数据
        String email = binding.inputEmail.getEditValue();
        mCountDownHelper.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/send_code?email=" + email , getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        Utils.showResponse(Utils.getString(getContext(), R.string.send_verify_code));
                    }
                });
            }
        }.start();
    }



    @Override
    protected TitleBar initTitle() {
        return super.initTitle();
    }

    @Override
    public void onDestroyView() {
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
        super.onDestroyView();

    }
}