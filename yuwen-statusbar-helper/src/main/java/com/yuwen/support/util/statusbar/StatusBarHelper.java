package com.yuwen.support.util.statusbar;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author yuwen 20161120
 */

public class StatusBarHelper {
    private static String TAG = "StatusBarHelper";

    /**
     * 设置沉浸式窗口，设置成功后，状态栏则透明显示
     *
     * @param window    需要设置的窗口
     * @param immersive 是否把窗口设置为沉浸
     * @return boolean 成功执行返回true
     */
    public static boolean setImmersiveWindow(Window window, boolean immersive) {
        boolean result = false;
        if (window != null) {
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            if (immersive) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
            result = true;
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色及黑色文字风格
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏颜色设置为深色
     * @return boolean  成功执行返回true
     */
    public static boolean setStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (isMIUI6Later()) {
            result = setMiuiStatusBarDarkMode(window, dark);
        } else if (isFlyme4Later()) {
            result = setFlymeStatusBarDarkMode(window, dark);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = setUsualStatusBarDarkMode(window, dark);
        }
        return result;
    }

    /**
     * 设置 MIUI 状态栏图标为深色及黑色文字风格
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏颜色设置为深色
     * @return boolean  成功执行返回true
     */
    private static boolean setMiuiStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        Class<? extends Window> clazz = window.getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, dark ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            Log.e(TAG, "setMiuiStatusBarDarkMode: failed");
        }
        return result;
    }

    /**
     * 设置 Flyme 状态栏图标为深色及黑色文字风格
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setFlymeStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                Log.e(TAG, "setFlymeStatusBarDarkIcon: failed");
            }
        }
        return result;
    }


    /**
     * 设置 Android 6.0 以上状态栏图标为深色及黑色文字风格
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setUsualStatusBarDarkMode(Window window, boolean dark) {
        // Android 6.0 以下不支持直接返回 False
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        boolean result = false;
        if (window != null) {
            try {
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                if (dark) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                window.getDecorView().setSystemUiVisibility(systemUiVisibility);
                result = true;
            } catch (Exception e) {
                Log.e(TAG, "setUsualStatusBarDarkMode: failed");
            }
        }
        return result;
    }

    /**
     * 判断是否为 MIUI6 以上
     */
    public static boolean isMIUI6Later() {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method mtd = clz.getMethod("get", String.class);
            String val = (String) mtd.invoke(null, "ro.miui.ui.version.name");
            val = val.replaceAll("[vV]", "");
            int version = Integer.parseInt(val);
            return version >= 6;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否 Flyme4 以上
     */
    public static boolean isFlyme4Later() {
        return Build.FINGERPRINT.contains("Flyme_OS_4")
                || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find()
                || Build.DISPLAY.contains("Flyme 5");
    }

    /**
     * 获取ActionBar高度
     *
     * @param context   上下文
     * @param actionBar 对应的ActionBar
     * @return int ActionBar的高度值
     */
    public static int getActionBarHeight(Context context, ActionBar actionBar) {
        Resources.Theme theme = context.getTheme();
        if (theme != null && actionBar != null) {
            TypedValue value = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, value, true)) {
                return TypedValue.complexToDimensionPixelSize(
                        value.data, context.getResources().getDisplayMetrics());
            }
            return actionBar.getHeight();
        }
        return 0;
    }

    /**
     * 创建一个状态栏高度
     *
     * @param context 上下文
     * @param view    要设置的布局
     * @return int 状态栏高度
     */
    public static void setStatusBarHeight(Context context, View view) {
        view.setPadding(
                view.getPaddingLeft(),
                StatusBarHelper.getStatusBarHeight(context),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return int 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        // 获得状态栏高度在系统中的的Id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}