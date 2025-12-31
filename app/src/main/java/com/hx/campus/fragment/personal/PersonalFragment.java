/*
 * Copyright (C) 2019 ()
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hx.campus.fragment.personal;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.core.webview.AgentWebActivity;
import com.hx.campus.databinding.FragmentProfileBinding;
import com.hx.campus.fragment.other.AboutFragment;
import com.hx.campus.fragment.personal.user.AccountFragment;
import com.hx.campus.fragment.personal.user.PhotoFragment;
import com.hx.campus.fragment.personal.user.SuggestionFragment;
import com.hx.campus.fragment.settings.SettingsFragment;
import com.hx.campus.utils.Utils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

/**
 * @author xuexiang
 * @since 2019-10-30 00:18
 */
//个人中心
@Page(anim = CoreAnim.none)
public class PersonalFragment extends BaseFragment<FragmentProfileBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentProfileBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentProfileBinding.inflate(inflater, container, attachToRoot);
    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.menu_profile);
    }
    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        initAc();//初始化账户数据

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

    @Override
    protected void initListeners() {
        binding.photo.setOnSuperTextViewClickListener(this);
        binding.account.setOnSuperTextViewClickListener(this);
        binding.tips.setOnSuperTextViewClickListener(this);
        binding.suggestion.setOnSuperTextViewClickListener(this);
        binding.menuSettings.setOnSuperTextViewClickListener(this);
        binding.menuAbout.setOnSuperTextViewClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        switch (id) {
            case R.id.photo:
                //设置头像
                openNewPage(PhotoFragment.class);
                break;
            case R.id.account:
                //账户页
                openNewPage(AccountFragment.class);
                break;
            case R.id.tips:
                //公告页
                //获取app当前语言
                String currentLanguage = Utils.language(getContext());
                if(currentLanguage.equals("zh")){
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification.html", getContext()));
                } else if (currentLanguage.equals("en")) {
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification_en.html", getContext()));
                }
                break;
            case R.id.suggestion:
                //帮助与反馈
                openNewPage(SuggestionFragment.class);
                break;
            case R.id.menu_settings:
                //设置页面
                openNewPage(SettingsFragment.class);
                break;
            case R.id.menu_about:
                //关于页面
                openNewPage(AboutFragment.class);
                break;


        }
    }
}
