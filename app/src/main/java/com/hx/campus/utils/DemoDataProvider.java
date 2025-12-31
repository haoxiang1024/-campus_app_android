package com.hx.campus.utils;



import android.content.Context;
import android.graphics.drawable.Drawable;

import com.hx.campus.R;
import com.hx.campus.adapter.entity.NewInfo;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2018/11/23 下午5:52
 */
public class DemoDataProvider {

    /**
     * 定义Handler常量
     */
    private static final int MSG_GET_NEWS_LIST_SUCCESS = 1;

    public static String[] titles = new String[]{
            "紧急通知",
            "意见反馈",
            "app闪退",
            "隐私",
    };
    public static String newsKey = "9fbfe1092fa33bf4bf99d8b6a661963e";//新闻key
    //    public static String newsKey = "bec633393690881151584f0ce9462ecf";//新闻key
    public static String APPID = "nwrkqmmklavajgpp";//新闻接口id
    public static String APPSECRET = "b0MwcllXNVB4eHdBaDN1cFFqQmR0QT09";//新闻接口密钥

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "https://p5.itc.cn/q_70/images03/20211108/6811c0f831614cebbabe194bdf4566cf.jpeg",//紧急通知"
            "https://www.lmlccdn.com/www.lmlc.com.new/cdn/desktop/popular/feedback/img/header.6284a0d800.jpg",//意见反馈
            "https://p8.itc.cn/q_70/images03/20210322/e3b1fd5131054817a2430aee925f86a4.png",//app帮助
            "https://demons.yirendai.com/introductionPages/image/privacy-icon.png",//隐私
    };

    @MemoryCache
    public static List<BannerItem> getBannerList() {
        List<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];
            list.add(item);
        }
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getDemoNewInfos() {
        //定义一个空列表，用于存储解析出来的新闻对象
        final List<NewInfo> list = new ArrayList<>();
        list.add(new NewInfo("热点", "皇姑区通报幼儿呕吐腹泻为诺如病毒感染")
                .setSummary("据皇姑发布8日上午转引皇姑区教育局信息，蒲公英实验幼儿园幼儿呕吐、腹泻问题，经组织区疾控部门对幼儿呕吐物进行实验室检测，判定为诺如病毒感染，现已按疾控专家意见开展相关工作。")
                .setDetailUrl("https://mini.eastday.com/mobile/230608142749842696757.html")
                .setUserName("中国日报网")
                .setImageUrl("https://dfzximg02.dftoutiao.com/news/20230608/20230608142749_2ed63d83a7096787bf41b6ca843e0721_1_mwpm_03201609.jpeg"));

        list.add(new NewInfo("热点", "牵挂那颗“致富果” 她重返母校当科研助理")
                .setSummary("一夜春雨浸润了土壤，也让漫山遍野、果实挂满枝头的李子树越发翠绿。5月10日上午，宜宾市屏山县好李园基地内，几台实时监控着基地温度、湿度、土壤酸碱度等数据的智慧农业前端数据采集器引人注目。")
                .setDetailUrl("https://mini.eastday.com/nsa/230608142131387157627.html?qid=02263&vqid=qid02650")
                .setUserName("每日看点快看")
                .setImageUrl("https://dfzximg02.dftoutiao.com//news//20230608//20230608142131_3e008cf638e8fa0975e067abc3afce88_2_mwpm_03201609.jpeg"));

        list.add(new NewInfo("热点", "有一种浪漫叫风吹麦浪")
                .setSummary("麦浪滚滚，颗粒归仓。6月7日，山西临汾大面积小麦喜迎丰收，金黄的麦子层层叠叠，构成一幅美丽的画卷。戳视频，一起感受风吹麦浪的浪漫！")
                .setDetailUrl("https://www.163.com/news/article/I6MRVR65000189FH.html")
                .setUserName("网易新闻")
                .setImageUrl("http://img2.zjolcdn.com/pic/003/007/972/00300797245_1e551bd5.jpg"));

        list.add(new NewInfo("热点", "男子自称“家里做烟草，每月刷父亲卡零花钱20万”，当地多部门回应")
                .setSummary("6月7日，一年轻男子在视频中自称家里是做烟草的，每月刷父亲卡零花钱20万元，引发广泛关注。有网友称，男子的父亲是广东省梅州烟草专卖局的。6月7日下午，梅州烟草专卖局回应极目新闻记者称，他们也已关注到了相关视频，初步排查没有员工认识视频中的男子.")
                .setDetailUrl("https://new.qq.com/rain/a/20230607A08H2600")
                .setUserName("腾讯新闻")
                .setImageUrl("https://inews.gtimg.com/om_bt/OMQcYZVOxtjvfAIbRjjM7aWQpuldAUMmc7hXixc8asZWkAA/1000"));

        list.add(new NewInfo("音乐", "HYBE利用AI声音技术发行多语言K-pop歌曲；什么是Spotify代码及其工作原理？")
                .setSummary("近日，韩国娱乐巨头HYBE发行了一首名为《Masquerade》的新单曲，由一位叫MIDNATT的艺人演唱，这首歌被HYBE称为“有史以来第一首用韩语、英语、日语、中文、西班牙语和越南语制作的多语言歌曲。")
                .setDetailUrl("https://www.163.com/dy/article/I4T2Q3470517HP9B.html")
                .setUserName("网易号")
                .setImageUrl("https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0516%2Fdec5d227j00rur8qz0015d200ku00b4g00it00a1.jpg&thumbnail=660x2147483647&quality=80&type=jpg"));
        return list;
    }


    public static List<AdapterItem> getGridItems(Context context) {
        return getGridItems(context, R.array.grid_titles_entry, R.array.grid_icons_entry);
    }


    private static List<AdapterItem> getGridItems(Context context, int titleArrayId, int iconArrayId) {
        List<AdapterItem> list = new ArrayList<>();
        String[] titles =context.getResources().getStringArray(titleArrayId);
        Drawable[] icons = ResUtils.getDrawableArray(context, iconArrayId);
        for (int i = 0; i < titles.length; i++) {
            list.add(new AdapterItem(titles[i], icons[i]));
        }
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getEmptyNewInfo() {
        List<NewInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new NewInfo());
        }
        return list;
    }

}
