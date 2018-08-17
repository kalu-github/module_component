package permission.kalu.core.listener;

import android.content.Intent;

import java.util.List;

/**
 * description: 注解回调方法
 * created by kalu on 2017/12/16 17:03
 */
public interface OnAnnotationChangeListener<T> {

    String NAME = "$$OnAnnotationChangeListener";

    void onSucc(T object, int requestCode, List<String> list);

    void onFail(T object, int requestCode, List<String> list);

    void onAgain(T object, int requestCode, List<String> list);

    void onDenied(T object, int requestCode, List<String> list, Intent intent);
}