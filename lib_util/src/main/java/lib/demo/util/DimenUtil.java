package lib.demo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * description: 尺寸
 * created by kalu on 2018/5/6 19:35
 */
public final class DimenUtil {

    public static float sp2px(Context context, float sp) {

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    public static float px2sp(Context context, float px) {

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.scaledDensity;
    }

    public static float dp2px(Context context, float dp) {

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * metrics.density;
    }

    public static int dp2px(Context context, int dp) {

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    public static float px2dp(Context context, float px) {

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.density;
    }
}
