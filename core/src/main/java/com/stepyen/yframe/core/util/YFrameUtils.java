
package com.stepyen.yframe.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.stepyen.yframe.core.core.AppManager;
import com.stepyen.yframe.core.core.app.IApp;
import com.stepyen.yframe.core.core.app.IAppComponent;

import java.security.MessageDigest;
import java.util.List;

import androidx.annotation.StringRes;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ================================================
 * 一些框架常用的工具
 */
public class YFrameUtils {

    private static Context mContext;

    private YFrameUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        Preconditions.checkNotNull(mContext, "请先在全局Application中调用 XFrameUtils.init() 初始化！");
        return mContext;
    }

    public static Context getAppContext() {
        Preconditions.checkNotNull(mContext, "请先在全局Application中调用 XFrameUtils.init() 初始化！");
        return mContext.getApplicationContext();
    }

    /**
     * dp 转 px
     *
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dp2px( float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int px2dp( int pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp( float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return mContext.getResources();
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return (int) getResources().getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     * @param dimenName
     * @return
     */
    public static float getDimens( String dimenName) {
        return getResources().getDimension(getResources().getIdentifier(dimenName, "dimen", mContext.getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */
    public static String getString(int stringID) {
        return getResources().getString(stringID);
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */
    public static String getString( String strName) {
        return getString(getResources().getIdentifier(strName, "string", mContext.getPackageName()));
    }

    public static String getStringFormat(@StringRes int resId, Object... args) {
        return String.format(getString(resId), args);
    }

    public static String getText(TextView tv) {
        if (tv == null) {
            return "";
        }

        return tv.getText().toString().trim();
    }

    /**
     * 获得颜色
     */
    public static int getColor(int rid) {
        return getResources().getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor( String colorName) {
        return getColor( getResources().getIdentifier(colorName, "color", mContext.getPackageName()));
    }



    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }


    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    public static Drawable getDrawable( int rID) {
        return getResources().getDrawable(rID);
    }


    public static void startActivity(Class activityClass) {
        AppManager.getAppManager().startActivity(activityClass);
    }

    public static void startActivity(Intent content) {
        AppManager.getAppManager().startActivity(content);
    }

    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth() {
        return getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isNoEmpty(String string) {
        if (string == null || TextUtils.isEmpty(string)) {
            return false;
        }

        return true;
    }

    public static boolean isNoEmpty(List list) {
        if (list == null ||list.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean isNoEmpty(Object object) {
        if (object == null ) {
            return false;
        }

        return true;
    }


    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 执行 {@link AppManager#killAll()}
     */
    public static void killAll() {
        AppManager.getAppManager().killAll();
    }

    /**
     * 执行 {@link AppManager#appExit()}
     */
    public static void exitApp() {
        AppManager.getAppManager().appExit();
    }


    public static IAppComponent getAppComponent() {
        Preconditions.checkNotNull(mContext, "%s cannot be null", Context.class.getName());
        return ((IApp) mContext.getApplicationContext()).getAppComponent();
    }

}
