
package com.hx.campus.utils;

import static com.hx.campus.core.webview.AgentWebFragment.KEY_URL;
import static com.hx.campus.fragment.other.ServiceProtocolFragment.KEY_IS_IMMERSIVE;
import static com.hx.campus.fragment.other.ServiceProtocolFragment.KEY_PROTOCOL_TITLE;
import static com.xuexiang.xutil.XUtil.getContext;
import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hx.campus.R;
import com.hx.campus.adapter.entity.User;
import com.hx.campus.core.webview.AgentWebActivity;
import com.hx.campus.fragment.other.ServiceProtocolFragment;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xui.utils.ColorUtils;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.toast.XToast;
import com.xuexiang.xutil.XUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;


public final class Utils {

    /**
     * 这里填写你的应用隐私政策网页地址
     */
    private static final String PRIVACY_URL = "https://gitee.com/xuexiangjys/TemplateAppProject/raw/master/LICENSE";

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 显示隐私政策的提示
     *
     * @param context
     * @param submitListener 同意的监听
     * @return
     */
    public static Dialog showPrivacyDialog(Context context, MaterialDialog.SingleButtonCallback submitListener) {
        MaterialDialog dialog = new MaterialDialog.Builder(context).title(R.string.title_reminder).autoDismiss(false).cancelable(false)
                .positiveText(R.string.lab_agree).onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                })
                .negativeText(R.string.lab_disagree).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        DialogLoader.getInstance().showConfirmDialog(context, ResUtils.getString(R.string.title_reminder), String.format(ResUtils.getString(R.string.content_privacy_explain_again), ResUtils.getString(R.string.app_name)), ResUtils.getString(R.string.lab_look_again), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                showPrivacyDialog(context, submitListener);
                            }
                        }, ResUtils.getString(R.string.lab_still_disagree), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DialogLoader.getInstance().showConfirmDialog(context, ResUtils.getString(R.string.content_think_about_it_again), ResUtils.getString(R.string.lab_look_again), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        showPrivacyDialog(context, submitListener);
                                    }
                                }, ResUtils.getString(R.string.lab_exit_app), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        XUtil.exitApp();
                                    }
                                });
                            }
                        });
                    }
                }).build();
        dialog.setContent(getPrivacyContent(context));
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }

    /**
     * @return 隐私政策说明
     */
    private static SpannableStringBuilder getPrivacyContent(Context context) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder()
                .append("    欢迎来到").append(ResUtils.getString(R.string.app_name)).append("!\n")
                .append("    我们深知个人信息对你的重要性，也感谢你对我们的信任。\n")
                .append("    为了更好地保护你的权益，同时遵守相关监管的要求，我们将通过");
        stringBuilder.append(getPrivacyLink(context, PRIVACY_URL))
                .append("向你说明我们会如何收集、存储、保护、使用及对外提供你的信息，并说明你享有的权利。\n")
                .append("    更多详情，敬请查阅")
                .append(getPrivacyLink(context, PRIVACY_URL))
                .append("全文。");
        return stringBuilder;
    }

    /**
     * @param context 隐私政策的链接
     * @return
     */
    private static SpannableString getPrivacyLink(Context context, String privacyUrl) {
        String privacyName = String.format(ResUtils.getString(R.string.lab_privacy_name), ResUtils.getString(R.string.app_name));
        SpannableString spannableString = new SpannableString(privacyName);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goWeb(context, privacyUrl);
            }
        }, 0, privacyName.length(), Spanned.SPAN_MARK_MARK);
        return spannableString;
    }


    /**
     * 请求浏览器
     *
     * @param url
     */
    public static void goWeb(Context context, final String url) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }


    /**
     * 打开用户协议和隐私协议
     *
     * @param fragment
     * @param isPrivacy   是否是隐私协议
     * @param isImmersive 是否沉浸式
     */
    public static void gotoProtocol(XPageFragment fragment, boolean isPrivacy, boolean isImmersive) {
        PageOption.to(ServiceProtocolFragment.class)
                .putString(KEY_PROTOCOL_TITLE, isPrivacy ? ResUtils.getString(R.string.title_privacy_protocol) : ResUtils.getString(R.string.title_user_protocol))
                .putBoolean(KEY_IS_IMMERSIVE, isImmersive)
                .open(fragment);

    }

    /**
     * 是否是深色的颜色
     *
     * @param color
     * @return
     */
    public static boolean isColorDark(@ColorInt int color) {
        return ColorUtils.isColorDark(color, 0.382);
    }

    /**
     * @param reurl   需要构造的url
     * @param context 上下文
     * @return 返回url
     */


    public static String rebuildUrl(String reurl, Context context) {
        //读取url资源文件
        String endUrl = "";
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.LoadProperties(context);
        String url = properties.getProperty("url");
        //改造url
        if (url != null) {
            endUrl = url + reurl;
        }
        return endUrl;
    }

    //时间格式转换
    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //存储Javabean
    public static <T> void saveBean2Sp(Context context, T t, String fileName, String keyName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] bytes = bos.toByteArray();
            String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(keyName, ObjStr);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取javabean
    public static <T extends Object> T getBeanFromSp(Context context, String fileName, String keyNme) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        byte[] bytes = Base64.decode(preferences.getString(keyNme, ""), Base64.DEFAULT);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 图片真实路径获取
     */
    public static String getRealPath(Context context, Intent data) {
        // 判断手机系统版本号
        if (Build.VERSION.SDK_INT >= 19) {
            // 4.4及以上系统使用这个方法处理图片
            return handleImageOnKitKat(context, data);
        } else {
            // 4.4以下系统使用这个方法处理图片
            return handleImageBeforeKitKat(context, data);
        }
    }

    @TargetApi(19)
    private static String handleImageOnKitKat(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //displayImage(imagePath); // 根据图片路径显示图片
        return imagePath;
    }

    private static String handleImageBeforeKitKat(Context context, Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(context, uri, null);
        return imagePath;
    }


    @SuppressLint("Range")
    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //ui操作，提示框
    public static void showResponse(final String response) {
        runOnUiThread(() -> {
            // 在这里进行UI操作，将结果显示到界面上
            XToast.info(getContext(), response).show();
        });
    }
    //获取服务端返回的用户数据并存储


    public static void doUserData(User user) {
        if (user == null) return;
        //存储SharedPreferences以便之后调用
        Utils.saveBean2Sp(getContext(), user, "User", "user");
    }

    //判断当前语言
    public static String language(Context context) {
        //获取app当前语言
        Locale currentLocale = context.getResources().getConfiguration().locale;
        return currentLocale.getLanguage();
    }

    //根据资源id读取字符串
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

}
