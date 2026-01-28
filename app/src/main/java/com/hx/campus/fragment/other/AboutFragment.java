/*
 * Copyright (C) 2021 xuexiangjys()
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

package com.hx.campus.fragment.other;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hx.campus.R;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentAboutBinding;
import com.hx.campus.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xuexiang.xutil.app.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Page()
public class AboutFragment extends BaseFragment<FragmentAboutBinding> {
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.about);
    }
    @Override
    protected void initViews() {
        binding.tvVersion.setText(String.format("版本号：%s", AppUtils.getAppVersionName()));

        XUIGroupListView.newSection(getContext())
                //.addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_homepage)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_project_github)))
//                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_author_github)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_author_github)))
//                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_donation_link)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_donation_link)))
//                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_add_qq_group)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_add_qq_group)))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.title_user_protocol)), v -> Utils.gotoProtocol(this, false, false))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.title_privacy_protocol)), v -> Utils.gotoProtocol(this, true, false))
                .addTo(binding.aboutList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new Date());
        binding.tvCopyright.setText(String.format(getResources().getString(R.string.about_copyright), currentYear));
    }

    @NonNull
    @Override
    protected FragmentAboutBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentAboutBinding.inflate(inflater, container, attachToRoot);
    }
}
