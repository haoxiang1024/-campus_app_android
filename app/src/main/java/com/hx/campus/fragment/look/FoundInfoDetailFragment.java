package com.hx.campus.fragment.look;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.Found;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentFoundInfoDetailBinding;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Response;

@Page()
public class FoundInfoDetailFragment extends BaseFragment<FragmentFoundInfoDetailBinding> {

    public static final String KEY_FOUND = "found";


    @AutoWired(name = KEY_FOUND)
    Found found;//实体类不能序列化，否则无法注入

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
        return getResources().getString(R.string.found_info_detail);
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
        String[] statuses = {"已认领", "未认领"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.state.setAdapter(adapter);
        int position = Arrays.asList(statuses).indexOf(found.getState());
        binding.state.setSelection(position);
        binding.state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 处理选中的项
                String selected = (String) parent.getItemAtPosition(position);
                //提交状态
                binding.sumbitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                OkhttpUtils.get(Utils.rebuildUrl("/updateFoundState?id=" + found.getId() + "&state=" + selected + "&user_id=" + found.getId(), getContext()), new OkHttpCallback() {
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        super.onResponse(call, response);
                                        Utils.showResponse(Utils.getString(getContext(),R.string.submit_success));
                                    }
                                });
                            }
                        }.start();

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 未选择任何项
                Utils.showResponse(Utils.getString(getContext(),R.string.no_selection_made));
            }
        });
        //设置发布日期
        String date = Utils.dateFormat(found.getPub_date());
        binding.tvDate.setText(date);
    }


    @NonNull
    @Override
    protected FragmentFoundInfoDetailBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentFoundInfoDetailBinding.inflate(inflater, container, attachToRoot);
    }
}