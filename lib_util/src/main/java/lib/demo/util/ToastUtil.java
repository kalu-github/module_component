package lib.demo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * description: 消息
 * created by kalu on 2018/5/6 17:26
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context, String content) {
        if (null == mToast) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }
}
