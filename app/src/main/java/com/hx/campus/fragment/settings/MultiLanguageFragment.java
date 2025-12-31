package com.hx.campus.fragment.settings;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hx.campus.R;
import com.hx.campus.activity.MainActivity;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentMultiLanguageBinding;
import com.hx.campus.utils.LanguageUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.Locale;

@Page
public class MultiLanguageFragment extends BaseFragment<FragmentMultiLanguageBinding> implements SuperTextView.OnSuperTextViewClickListener {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentMultiLanguageBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentMultiLanguageBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.multi_language);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.systemLanguage.setOnSuperTextViewClickListener(this);
        binding.simpleCn.setOnSuperTextViewClickListener(this);
        binding.en.setOnSuperTextViewClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        switch (id) {
            case R.id.system_language:
                //获取系统语言
                Locale locale = Locale.getDefault();
                String language = locale.getLanguage();
                //更改语言并保存
                LanguageUtil.changeAppLanguage(getActivity(), language, MainActivity.class);
                break;
            case R.id.simple_cn:
                //简体中文
                LanguageUtil.changeAppLanguage(getActivity(), "zh", MainActivity.class);
                break;
            case R.id.en:
                //英语
                LanguageUtil.changeAppLanguage(getActivity(), "en", MainActivity.class);
                break;

        }

    }
}