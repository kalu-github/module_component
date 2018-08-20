package lib.kalu.core.frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import lib.demo.util.ToastUtil;

/**
 * description: 公共方法
 * created by kalu on 2018/8/16 10:42
 */
public interface BaseView {

    default void setTexts(TextView view, String str) {
        if (null == view)
            return;
        view.setText(str);
    }

    default void setTexts(TextView view, int strid) {
        if (null == view)
            return;

        final Context applicationContext = view.getContext().getApplicationContext();
        if (null == applicationContext)
            return;

        final String string = applicationContext.getResources().getString(strid);
        view.setText(string);
    }

    default void setImageBitmaps(ImageView view, Bitmap bitmap) {
        if (null == view)
            return;
        if (null == bitmap)
            return;

        view.setImageBitmap(bitmap);
    }

    default void setImageResources(ImageView view, int resid) {
        if (null == view)
            return;

        try {
            view.setImageResource(resid);
        } catch (Exception e) {
            final Context applicationContext = view.getContext().getApplicationContext();
            if (null == applicationContext)
                return;
            ToastUtil.showToast(applicationContext, "出错了");
        }
    }

}
