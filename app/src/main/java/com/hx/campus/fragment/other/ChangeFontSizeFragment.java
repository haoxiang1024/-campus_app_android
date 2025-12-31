package com.hx.campus.fragment.other;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentChangeFontSizeBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "调整字体大小")
public class ChangeFontSizeFragment extends BaseFragment<FragmentChangeFontSizeBinding> {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentChangeFontSizeBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentChangeFontSizeBinding.inflate(inflater,container,false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }
}