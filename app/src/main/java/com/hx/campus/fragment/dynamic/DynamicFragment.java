

package com.hx.campus.fragment.dynamic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.hx.campus.R;
import com.hx.campus.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.hx.campus.adapter.base.delegate.SimpleDelegateAdapter;
import com.hx.campus.adapter.base.delegate.SingleDelegateAdapter;
import com.hx.campus.adapter.entity.Found;
import com.hx.campus.adapter.entity.Lost;
import com.hx.campus.adapter.entity.NewInfo;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.core.webview.AgentWebActivity;
import com.hx.campus.databinding.FragmentNewsBinding;
import com.hx.campus.fragment.navigation.FoundFragment;
import com.hx.campus.fragment.navigation.LostFragment;
import com.hx.campus.fragment.navigation.content.FoundDetailFragment;
import com.hx.campus.fragment.navigation.content.LostDetailFragment;
import com.hx.campus.fragment.other.SearchFragment;
import com.hx.campus.utils.DemoDataProvider;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;
import com.hx.campus.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.samlss.broccoli.Broccoli;
import okhttp3.Call;
import okhttp3.Response;


@Page(anim = CoreAnim.fade)
public class DynamicFragment extends BaseFragment<FragmentNewsBinding> {

    public static String newsKey = "9fbfe1092fa33bf4bf99d8b6a661963e";//新闻key
    private SimpleDelegateAdapter<NewInfo> mNewsAdapter;
    private DelegateAdapter delegateAdapter;
    private List<NewInfo> list = new ArrayList<>();

    @NonNull
    @Override
    protected FragmentNewsBinding viewBindingInflate(@NonNull LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentNewsBinding.inflate(inflater, container, attachToRoot);
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
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        binding.recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        //轮播条
        SingleDelegateAdapter bannerAdapter = new SingleDelegateAdapter(R.layout.include_head_view_banner) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                SimpleImageBanner banner = holder.findViewById(R.id.sib_simple_usage);
                banner.setSource(DemoDataProvider.getBannerList())
                        .setOnItemClickListener(new BaseBanner.OnItemClickListener<BannerItem>() {
                            @Override
                            public void onItemClick(View view, BannerItem item, int position) {
                                //获取app当前语言
                                String currentLanguage = Utils.language(getContext());
                                if (currentLanguage.equals("zh")) {
                                    //中文页
                                    switch (item.title) {
                                        case "紧急通知":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification.html", getContext()));
                                            break;
                                        case "意见反馈":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/contract.html", getContext()));
                                            break;
                                        case "app闪退":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/appcrash.html", getContext()));
                                            break;
                                        case "隐私":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/privacy.html", getContext()));
                                            break;
                                    }
                                } else if (currentLanguage.equals("en")) {
                                    //英文页
                                    switch (item.title) {
                                        case "紧急通知":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification_en.html", getContext()));
                                            break;
                                        case "意见反馈":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/contract_en.html", getContext()));
                                            break;
                                        case "app闪退":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/appcrash_en.html", getContext()));
                                            break;
                                        case "隐私":
                                            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/privacy_en.html", getContext()));
                                            break;
                                    }
                                }
                            }
                        }).startScroll();
            }
        };

        //九宫格菜单
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        SimpleDelegateAdapter<AdapterItem> commonAdapter = new SimpleDelegateAdapter<AdapterItem>(R.layout.adapter_common_grid_item, gridLayoutHelper, DemoDataProvider.getGridItems(getContext())) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, AdapterItem item) {
                if (item != null) {
                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
                    imageView.setCircle(true);
                    ImageLoader.get().loadImage(imageView, item.getIcon());
                    holder.text(R.id.tv_title, item.getTitle().toString().substring(0, 1));
                    holder.text(R.id.tv_sub_title, item.getTitle());
                    holder.click(R.id.ll_container, v -> {
                        // 注意: 这里由于NewsFragment是使用Viewpager加载的，并非使用XPage加载的，因此没有承载Activity， 需要使用openNewPage。
                        //获取标题,根据标题打开不同的页面
                        String title = item.getTitle().toString();
                        String[] stringArray = getResources().getStringArray(R.array.grid_titles_entry);//根据app语言获取不同的数据
                        if (title.equals(stringArray[0])) {
                            openNewPage(LostFragment.class, LostFragment.KEY_TITLE_NAME, title);
                        } else if (title.equals(stringArray[1])) {
                            openNewPage(FoundFragment.class, FoundFragment.KEY_TITLE_NAME, title);
                        } else if (title.equals(stringArray[2])) {
                            openNewPage(SearchFragment.class);
                        } else if (title.equals(stringArray[3])) {
                            String language = Utils.language(getContext());
                            if (language.equals("en")) {
                                AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/contract_en.html", getContext()));
                            } else {
                                AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/contract.html", getContext()));

                            }
                        }

                    });
                }
            }
        };

        //推荐的标题
        SingleDelegateAdapter titleAdapter = new SingleDelegateAdapter(R.layout.adapter_title_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                String text = getResources().getString(R.string.recommendation);
                holder.text(R.id.tv_title, text);
            }
        };

        //推荐
        mNewsAdapter = new BroccoliSimpleDelegateAdapter<NewInfo>(R.layout.adapter_news_card_view_list_item, new LinearLayoutHelper(), DemoDataProvider.getEmptyNewInfo()) {


            @Override
            protected void onBindData(RecyclerViewHolder holder, NewInfo model, int position) {
                if (model != null) {
                    holder.text(R.id.tv_user_name, model.getUserName());
                    holder.text(R.id.tv_tag, model.getTag());
                    holder.text(R.id.tv_title, model.getTitle());
                    holder.text(R.id.tv_summary, model.getSummary());
                    holder.image(R.id.iv_image, model.getImageUrl());
                    holder.click(R.id.card_view, v -> {
                        if (position > list.size()) {
                            Utils.showResponse(Utils.getString(getContext(), R.string.pull_to_refresh));
                            return;
                        }
                        NewInfo newInfo = getItem(position);
                        //重构lost
                        Lost lost = new Lost(newInfo.getTitle(), newInfo.getImageUrl(), newInfo.getPub_Date(), newInfo.getSummary(), newInfo.getPlace(), newInfo.getPhone(), newInfo.getState(), newInfo.getUserName());
                        //重构found
                        Found found = new Found(newInfo.getTitle(), newInfo.getImageUrl(), newInfo.getPub_Date(), newInfo.getSummary(), newInfo.getPlace(), newInfo.getPhone(), newInfo.getState(), newInfo.getUserName());
                        if (newInfo.getState().equals("未找到")) {
                            //跳转失物详情页
                            openNewPage(LostDetailFragment.class, LostDetailFragment.KEY_LOST, lost);
                        } else {
                            //跳转招领页
                            openNewPage(FoundDetailFragment.class, FoundDetailFragment.KEY_FOUND, found);
                        }
                    });
                }
            }

            /**
             * 获取列表项
             *
             * @param position
             * @return
             */
            @Override
            public NewInfo getItem(int position) {
                return getNewInfo().get(position);
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.tv_user_name),
                        holder.findView(R.id.tv_tag),
                        holder.findView(R.id.tv_title),
                        holder.findView(R.id.tv_summary),
                        holder.findView(R.id.iv_image)
                );
            }
        };
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(commonAdapter);
        delegateAdapter.addAdapter(titleAdapter);
        delegateAdapter.addAdapter(mNewsAdapter);
        binding.recyclerView.setAdapter(delegateAdapter);
        //第一次进入获取数据
        binding.refreshLayout.autoRefresh(1000);
        mNewsAdapter.refresh(getNewInfo());
        binding.refreshLayout.finishRefresh();

    }

    @Override
    protected void initListeners() {
        //下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.getLayout().postDelayed(() -> {
                mNewsAdapter.refresh(getNewInfo());
                refreshLayout.finishRefresh();
            }, 1000);
        });
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.getLayout().postDelayed(() -> {
                mNewsAdapter.loadMore(getNewInfo());
                refreshLayout.finishLoadMore();
            }, 1000);
        });

    }

    public List<NewInfo> getNewInfo() {
        //失物置顶信息添加
        new Thread() {
            @Override
            public void run() {
                super.run();
                //失物信息展示
                OkhttpUtils.get(Utils.rebuildUrl("/showLostList?stick=1", getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        List<Lost> newInfoList = JsonOperate.getList(result, Lost.class);
                        //若无置顶信息则不执行
                        if (newInfoList == null) {
                            return;
                        }
                        for (Lost lost : newInfoList) {
                            list.add(new NewInfo(lost.getLostfoundtype().getName(), lost.getTitle())
                                    .setSummary(lost.getContent())
                                    .setUserName(lost.getNickname())
                                    .setState(lost.getState())
                                    .setPhone(lost.getPhone())
                                    .setPlace(lost.getPlace())
                                    .setPub_Date(lost.getPubDate())
                                    .setImageUrl(lost.getImg()));
                        }

                    }
                });
                //招领信息展示
                OkhttpUtils.get(Utils.rebuildUrl("/showFoundList?stick=1", getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        List<Found> founds = JsonOperate.getList(result, Found.class);
                        //若无置顶信息则不执行
                        if (founds == null) {
                            return;
                        }
                        for (Found found : founds) {
                            list.add(new NewInfo(found.getLostfoundtype().getName(), found.getTitle())
                                    .setSummary(found.getContent())
                                    .setUserName(found.getNickname())
                                    .setState(found.getState())
                                    .setPhone(found.getPhone())
                                    .setPlace(found.getPlace())
                                    .setPub_Date(found.getPub_date())
                                    .setImageUrl(found.getImg()));
                        }
                    }
                });
            }
        }.start();
        //返回解析出来的列表
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.refreshLayout.autoRefresh(50);
    }
}
