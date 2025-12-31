

package com.hx.campus.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.BaseActivity;
import com.hx.campus.core.BaseFragment;
import com.hx.campus.databinding.ActivityMainBinding;
import com.hx.campus.fragment.dynamic.DynamicFragment;
import com.hx.campus.fragment.look.LookFragment;
import com.hx.campus.fragment.other.AboutFragment;
import com.hx.campus.fragment.other.SearchFragment;
import com.hx.campus.fragment.personal.PersonalFragment;
import com.hx.campus.fragment.personal.user.AccountFragment;
import com.hx.campus.fragment.settings.SettingsFragment;
import com.hx.campus.utils.TokenUtils;
import com.hx.campus.utils.Utils;
import com.hx.campus.utils.sdkinit.XUpdateInit;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.display.Colors;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener, Toolbar.OnMenuItemClickListener {

    private String[] mTitles;//标题数组

    @Override
    protected ActivityMainBinding viewBindingInflate(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        initListeners();
    }


    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        WidgetUtils.clearActivityBackground(this);
        //标题数组
        mTitles = getResources().getStringArray(R.array.home_titles);
        //初始化标题栏
        binding.includeMain.toolbar.setTitle(mTitles[0]);
        binding.includeMain.toolbar.inflateMenu(R.menu.menu_main);
        binding.includeMain.toolbar.setOnMenuItemClickListener(this);
        initHeader();//初始化侧边栏
        //主页内容填充
        BaseFragment[] fragments = new BaseFragment[]{
                new DynamicFragment(),//主页
                new LookFragment(),//查看信息页
                new PersonalFragment()//我的页面
        };
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        binding.includeMain.viewPager.setOffscreenPageLimit(mTitles.length - 1);//设置ViewPager预加载页面数量的方法。它指定了当前页面两侧（左侧和右侧）的未选中页面数量，这些页面都会被预加载
        binding.includeMain.viewPager.setAdapter(adapter);//viewpager 适配器
    }

    private void initData() {
        //已经登录成功设置token 下次无需重复登录
        TokenUtils.setToken("login_succeed_token");
        XUpdateInit.checkUpdate(this, false);
    }


    private void initHeader() {
        binding.navView.setItemIconTintList(null);
        View headerView = binding.navView.getHeaderView(0);
        LinearLayout navHeader = headerView.findViewById(R.id.nav_header);
        RadiusImageView ivAvatar = headerView.findViewById(R.id.iv_avatar);
        TextView tvAvatar = headerView.findViewById(R.id.tv_avatar);
        TextView tvSign = headerView.findViewById(R.id.tv_sign);
        ImageView sexView = headerView.findViewById(R.id.sex_photo);
        if (Utils.isColorDark(ThemeUtils.resolveColor(this, R.attr.colorAccent))) {
            tvAvatar.setTextColor(Colors.WHITE);
            tvSign.setTextColor(Colors.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_white));
            }
        } else {
            tvAvatar.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_title_text));
            tvSign.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_explain_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_gray_3));
            }
        }
        //获取存储对象
        User user = Utils.getBeanFromSp(this, "User", "user");
        //加载图片
        if (TextUtils.isEmpty(user.getPhoto())) {
            tvAvatar.setVisibility(View.GONE);
        } else {
            tvAvatar.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhoto()).into(ivAvatar);
        }
        //设置昵称
        tvAvatar.setText(user.getNickname());
        //设置简介
        //判断性别
        if (user.getSex().equals("男")) {
            //判断语言
            String language = Utils.language(this);
            if(language.equals("zh")){
                tvSign.setText("小哥哥");
            } else if (language.equals("en")) {
                tvSign.setText("Male");
            }
            //设置性别图标
            sexView.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.man).into(sexView);
        } else {
            //判断语言
            String language = Utils.language(this);
            if(language.equals("zh")){
                tvSign.setText("小姐姐");
            } else if (language.equals("en")) {
                tvSign.setText("FeMale");
            }
            //设置性别图标
            sexView.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.women).into(sexView);
        }
        navHeader.setOnClickListener(this);
    }


    protected void initListeners() {
        //页面切换行为
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.includeMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//同步页面状态
        //侧边栏点击事件
        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            //判断菜单是否有选中行为的菜单项有则切换,无则打开页面
            if (menuItem.isCheckable()) {
                binding.drawerLayout.closeDrawers();//关闭抽屉
                return handleNavigationItemSelected(menuItem);//打开被选中项
            } else {
                int id = menuItem.getItemId();
                if (id == R.id.nav_settings) {
                    //设置页
                    openNewPage(SettingsFragment.class);
                } else if (id == R.id.nav_about) {
                    //关于页
                    openNewPage(AboutFragment.class);
                } else if (id == R.id.nav_search) {
                    //搜索页
                    openNewPage(SearchFragment.class);
                }
            }
            return true;
        });
        //主页事件监听
        binding.includeMain.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = binding.includeMain.bottomNavigation.getMenu().getItem(position);//底部导航栏菜单选项
                binding.includeMain.toolbar.setTitle(item.getTitle());//设置被选中的页面的标题
                item.setChecked(true);//设置被选中的菜单项
                updateSideNavStatus(item);//更新侧边栏菜单选中状态
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.includeMain.bottomNavigation.setOnNavigationItemSelectedListener(this);//底部导航栏点击事件(onNavigationItemSelected)
    }

    /**
     * 处理侧边栏点击事件
     *
     * @param menuItem
     * @return
     */
    private boolean handleNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());//设置标题栏标题
            binding.includeMain.viewPager.setCurrentItem(index, false);//设置主页页面被选中页
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav_header) {
            openNewPage(AccountFragment.class);//打开账户页
        }
    }

    //================Navigation================//

    /**
     * 底部导航栏点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());//设置标题栏标题
            binding.includeMain.viewPager.setCurrentItem(index, false);//设置主页页面被选中页
            updateSideNavStatus(menuItem);
            return true;
        }
        return false;
    }

    /**
     * 更新侧边栏菜单选中状态
     * @param menuItem
     */
    private void updateSideNavStatus(MenuItem menuItem) {
        MenuItem side = binding.navView.getMenu().findItem(menuItem.getItemId());
        if (side != null) {
            side.setChecked(true);
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }


    @Override
    public void onExit() {
        XUtil.exitApp();
    }


}
