
package com.hx.campus.fragment.look;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentTrendingBinding;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;


//查看信息页
@Page(anim = CoreAnim.none)
public class LookFragment extends BaseFragment<FragmentTrendingBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentTrendingBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentTrendingBinding.inflate(inflater, container, attachToRoot);
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

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.lost.setOnSuperTextViewClickListener(this);
        binding.found.setOnSuperTextViewClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        if (view.getId() == binding.lost.getId()) {
            //失物信息页
            openNewPage(LostInfoFragment.class);
        } else if (view.getId() == binding.found.getId()) {
            //招领信息页
            openNewPage(FoundInfoFragment.class);
        }

    }
}
