package permission.kalu.core.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

import permission.kalu.core.intent.IntentType;
import permission.kalu.core.listener.OnAnnotationChangeListener;
import permission.kalu.core.listener.OnPermissionChangeListener;


/**
 * description: 接口方法
 * created by kalu on 2018/5/13 1:03
 */
public interface WrapperImp {

    int TARGET_ACTIVITY = 1;
    int TARGET_FRAGMENT = 2;

    String getClassName();

    Activity getActivity();

    Fragment getFragment();

    int getTarget();

    WrapperImp setForce(boolean force);

    boolean isForce();

    WrapperImp setPageType(@IntentType int pageType);

    @IntentType
    int getPageType();

    OnAnnotationChangeListener getAnnotationChangeListener(String className);

    OnPermissionChangeListener getPermissionChangeListener();

    WrapperImp setOnPermissionChangeListener(OnPermissionChangeListener listener);

    /*******************************************************************/

    int getRequestCode();

    WrapperImp setRequestCode(int code);

    WrapperImp setPermissionName(String... name);

    List<String> getPermission();

    /*******************************************************************/

    void request();
}
