package permission.kalu.core.wrapper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import permission.kalu.core.listener.OnAnnotationChangeListener;
import permission.kalu.core.listener.OnPermissionChangeListener;
import permission.kalu.core.support.SupportCheck;


/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/13 1:08
 */
public final class WrapperFragmentV4 extends WrapperAbstract {

    private final Fragment mFragment;

    public WrapperFragmentV4(Fragment fragmentV4) {
        this.mFragment = fragmentV4;
    }

    @Override
    public String getClassName() {
        return mFragment.getClass().getName();
    }

    @Override
    public Activity getActivity() {
        return mFragment.getActivity();
    }

    @Override
    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public int getTarget() {
        return TARGET_FRAGMENT;
    }

    @Override
    public void request() {

        final List<String> requestList = getPermission();
        if (null == requestList || requestList.isEmpty()) return;

        final int requestCode = getRequestCode();
        final Context context = getActivity().getApplicationContext();

        // 4.3
        if (SupportCheck.isUnderAndroid4_3()) {
            Log.e("Permission", "fragment(不需要处理权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final OnPermissionChangeListener api1 = getPermissionChangeListener();
            if (null != api1) {
                api1.onSucc(requestCode, requestList);
            } else {
                final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                if (null != api2) {
                    api2.onSucc(getFragment(), requestCode, requestList);
                } else {
                    api2.onFail(getFragment(), requestCode, requestList);
                }
            }
        }
        // 4.3-6.0
        else if (SupportCheck.isUnderAndroid6_0()) {
            Log.e("Permission", "fragment(特殊处理国产机型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
        }
        // 6.0
        else {
            Log.e("Permission", "fragment(通用规则处理权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final ArrayList<String> againList = new ArrayList<>();
            for (String name : requestList) {
                if (getFragment().shouldShowRequestPermissionRationale(name)) {
                    againList.add(name);
                }
            }
            if (!againList.isEmpty()) {
                final OnPermissionChangeListener api1 = getPermissionChangeListener();
                if (null != api1) {
                    api1.onAgain(requestCode, requestList);
                } else {
                    final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                    if (null != api2) {
                        api2.onAgain(getFragment(), requestCode, requestList);
                    }
                }
            }
            if (!requestList.isEmpty()) {
                final String[] strings = requestList.toArray(new String[requestList.size()]);
                getFragment().requestPermissions(strings, requestCode);
            }
        }
    }
}
