package permission.kalu.core.support;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import permission.kalu.core.intent.IntentType;
import permission.kalu.core.listener.OnAnnotationChangeListener;
import permission.kalu.core.listener.OnPermissionChangeListener;
import permission.kalu.core.platform.PlatformManager;
import permission.kalu.core.wrapper.WrapperImp;

/**
 * description: 权限检测
 * created by kalu on 2018/5/3 11:50
 */
public final class SupportPermission {

    public final static void premissionSucc(WrapperImp wrapper, List<String> list) {

        // 1.监听方式
        final OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
        if (null != api1) {
            Log.e("Permission", "premissionSucc[监听] ==> " + list.toString());
            api1.onSucc(wrapper.getRequestCode(), list);
        }
        // 2.注解方式
        else {
            final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(wrapper.getClassName());
            if (null != api2) {
                Log.e("Permission", "premissionSucc[注解] ==> " + list.toString());
                final boolean result = (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT);
                api2.onSucc(result ? wrapper.getFragment() : wrapper.getActivity(), wrapper.getRequestCode(), list);
            } else {
                premissionFail(wrapper, list);
            }
        }
    }

    public final static void premissionFail(WrapperImp wrapper, List<String> list) {

        final ArrayList<String> deniedList = new ArrayList<>();
        final ArrayList<String> failList = new ArrayList<>();

        for (String name : list) {

            if (SupportCheck.isDenied(wrapper, name)) {
                deniedList.add(name);
            } else {
                failList.add(name);
            }
        }

        if (deniedList.size() > 0) {
            Activity activity = wrapper.getActivity();
            boolean google = wrapper.getPageType() == IntentType.GOOGLE_SETTING;
            Intent intent = PlatformManager.getIntent(google, activity);

            OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
            if (null != api1) {
                Log.e("Permission", "premissionDenied[监听] ==> " + deniedList.toString());
                api1.onDenied(wrapper.getRequestCode(), deniedList, intent);
            } else {
                final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(wrapper.getClassName());
                if (null != api2) {
                    Log.e("Permission", "premissionDenied[注解] ==> " + deniedList.toString());
                    final boolean result = (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT);
                    api2.onDenied(result ? wrapper.getFragment() : wrapper.getActivity(), wrapper.getRequestCode(), deniedList, intent);
                }
            }
        }

        if (failList.size() > 0) {

            OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
            if (null != api1) {
                Log.e("Permission", "premissionFail[监听] ==> " + failList.toString());
                api1.onFail(wrapper.getRequestCode(), failList);
            } else {
                final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(wrapper.getClassName());
                if (null != api2) {
                    Log.e("Permission", "premissionFail[注解] ==> " + failList.toString());
                    final boolean result = (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT);
                    api2.onFail(result ? wrapper.getFragment() : wrapper.getActivity(), wrapper.getRequestCode(), failList);
                }
            }
        }
    }
}
