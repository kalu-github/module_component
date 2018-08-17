package lib.demo.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import java.util.List;

/**
 * description: 基本工具类
 * created by kalu on 2016/11/19 11:57
 */
public final class BaseUtil {

    /**
     * 防止按钮连续点击。
     */
    private static long lastClickTime = 1000;

    public static synchronized boolean isFastClick(long delay) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 0 && timeD < delay) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 判断某个界面是否在前台
     */
    public static boolean isForeground(Context context, String className) {

        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 点击图片变色（Tint着色方法）
     *
     * @param view
     * @param imageId       图片ID
     * @param selectorColor Selector 颜色资源文件
     */
    public static void setImageTint(Context context, ImageView view, int imageId, int selectorColor) {

        final Drawable originBitmapDrawable2 = context.getResources().getDrawable(imageId).mutate();
        view.setImageDrawable(tintDrawable(originBitmapDrawable2, context.getResources().getColorStateList(selectorColor)));
    }

    /**
     * tint着色方法
     *
     * @param drawable
     * @param color
     * @return
     */
    private static Drawable tintDrawable(Drawable drawable, ColorStateList color) {
        final Drawable tempDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(tempDrawable, color);
        return tempDrawable;
    }
}
