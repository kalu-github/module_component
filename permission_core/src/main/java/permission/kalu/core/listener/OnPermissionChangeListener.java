package permission.kalu.core.listener;

import android.content.Intent;

import java.util.List;

/**
 * description: 监听回调
 * created by kalu on 2018/6/8 8:57
 */
public interface OnPermissionChangeListener {

    void onSucc(int requestCode, List<String> list);

    void onFail(int requestCode, List<String> list);

    void onAgain(int requestCode, List<String> list);

    void onDenied(int requestCode, List<String> list, Intent settingIntent);
}
