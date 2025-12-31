package com.hx.campus.fragment.navigation.content;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.Found;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentFoundDetailBinding;
import com.hx.campus.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

@Page
public class FoundDetailFragment extends BaseFragment<FragmentFoundDetailBinding> {
    public static final String KEY_FOUND = "found";


    @AutoWired(name = KEY_FOUND)
    Found found;//实体类不能序列化，否则无法注入

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentFoundDetailBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentFoundDetailBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 初始化参数
     */
    @Override
    protected void initArgs() {
        super.initArgs();
        XRouter.getInstance().inject(this);
    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.detail);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        setViews();//设置控件
    }

    private void setViews() {
        //设置标题
        binding.tvLostTitle.setText(found.getTitle());
        //设置内容
        binding.tvLostContent.setText(found.getContent());
        //加载图片
        if (TextUtils.isEmpty(found.getImg())) {
            binding.imgLost.setVisibility(View.GONE);

        } else {
            binding.imgLost.setVisibility(View.VISIBLE);
            Glide.with(this).load(found.getImg()).into(binding.imgLost);
        }
        //设置失主名称
        binding.tvAuthor.setText(found.getNickname());
        //设置联系方式
        binding.tvPhonenum.setText(found.getPhone());
        //设置地点
        binding.location.setText(found.getPlace());
        //设置状态
        binding.state.setText(found.getState());
        //设置发布日期
        String date = Utils.dateFormat(found.getPub_date());
        binding.tvDate.setText(date);
    }
}
