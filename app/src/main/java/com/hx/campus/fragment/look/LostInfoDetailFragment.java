package com.hx.campus.fragment.look;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.Lost;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentLostInfoDetailBinding;
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
public class LostInfoDetailFragment extends BaseFragment<FragmentLostInfoDetailBinding> {

    public static final String KEY_LOST = "lost";


    @AutoWired(name = KEY_LOST)
    Lost lost;//实体类不能序列化，否则无法注入

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
        return getResources().getString(R.string.lost_info_detail);
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
    protected FragmentLostInfoDetailBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentLostInfoDetailBinding.inflate(inflater, container, attachToRoot);
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
        binding.tvLostTitle.setText(lost.getTitle());
        //设置内容
        binding.tvLostContent.setText(lost.getContent());
        //加载图片
        if (TextUtils.isEmpty(lost.getImg())) {
            binding.imgLost.setVisibility(View.GONE);

        } else {
            binding.imgLost.setVisibility(View.VISIBLE);
            Glide.with(this).load(lost.getImg()).into(binding.imgLost);
        }
        //设置失主名称
        binding.tvAuthor.setText(lost.getNickname());
        //设置联系方式
        binding.tvPhonenum.setText(lost.getPhone());
        //设置地点
        binding.location.setText(lost.getPlace());
        //设置状态
        String[] statuses = {"已找到", "未找到"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.state.setAdapter(adapter);
        int position = Arrays.asList(statuses).indexOf(lost.getState());
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
                                OkhttpUtils.get(Utils.rebuildUrl("/updateLostState?id=" + lost.getId() + "&state=" + selected + "&user_id=" + lost.getId(), getContext()), new OkHttpCallback() {
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
        String date = Utils.dateFormat(lost.getPubDate());
        binding.tvDate.setText(date);
    }
}