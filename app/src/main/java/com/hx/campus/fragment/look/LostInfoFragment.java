package com.hx.campus.fragment.look;

import static com.hx.campus.core.webview.AgentWebFragment.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hx.campus.R;
import com.hx.campus.adapter.entity.Lost;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.adapter.lostandfoundnav.LostDetailAdapter;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.FragmentLostInfoBinding;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

//显示失物信息页
@Page()
public class LostInfoFragment extends BaseFragment<FragmentLostInfoBinding> {
    private LostDetailAdapter lostDetailAdapter;//丢失物品详情adapter


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentLostInfoBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot)  {
        return FragmentLostInfoBinding.inflate(inflater, container, attachToRoot);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        startAnim();//显示加载动画
        initData();//初始化列表数据

    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.lost_info);
    }
    @Override
    protected void initListeners() {
        super.initListeners();
        //跳转丢失物品详情页面
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Lost lost = lostDetailAdapter.getItem(position);//获取lost实例
            openPage(LostInfoDetailFragment.class, LostInfoDetailFragment.KEY_LOST, lost);
        });
    }

    private void initData() {
        lostDetailAdapter = new LostDetailAdapter(getContext());
        binding.listview.setAdapter(lostDetailAdapter);
        getData();//请求数据
    }

    private void getData() {
        //获取用户信息
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/getAllLostUserId?user_id=" + user.getId(), getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        getActivity().runOnUiThread(LostInfoFragment.this::stopAnim);//结束加载动画
                        //没有发布信息
                        if (JsonOperate.getValue(result, "msg").equals("还未发布任何信息")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.no_info_posted_yet));
                            return;
                        }
                        getActivity().runOnUiThread(() -> setAdapter(result));//设置适配器
                    }
                });
            }
        }.start();

    }

    private void setAdapter(String result) {
        lostDetailAdapter = new LostDetailAdapter(getContext());
        binding.listview.setAdapter(lostDetailAdapter);
        //结果转换
        //数据list
        List detailList = JsonOperate.getList(result, Lost.class);
        Log.e(TAG, "setAdapter: " + detailList);
        //设置数据
        lostDetailAdapter.setData(detailList, 1);
    }

    //显示加载动画
    private void startAnim() {
        binding.avLoad.show();
    }

    //结束加载动画
    private void stopAnim() {
        binding.avLoad.hide();
    }
}