package permission.kalu.core.wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import permission.kalu.core.listener.OnAnnotationChangeListener;
import permission.kalu.core.listener.OnPermissionChangeListener;
import permission.kalu.core.support.SupportCheck;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/13 1:08
 */
public final class WrapperActivity extends WrapperAbstract {

    private final Activity activity;

    public WrapperActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String getClassName() {
        return activity.getClass().getName();
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public int getTarget() {
        return TARGET_ACTIVITY;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void request() {

        final List<String> requestList = getPermission();
        if (null == requestList || requestList.isEmpty()) return;

        final int requestCode = getRequestCode();
        final Context context = getActivity().getApplicationContext();

        // 系统版本： under 4.3
        if (SupportCheck.isUnderAndroid4_3()) {
            Log.e("Permission", "activity(不需要处理权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final OnPermissionChangeListener api1 = getPermissionChangeListener();
            if (null != api1) {
                api1.onSucc(requestCode, requestList);
            } else {
                final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                if (null != api2) {
                    api2.onSucc(getActivity(), requestCode, requestList);
                } else {
                    api2.onFail(getActivity(), requestCode, requestList);
                }
            }
        }
        // 系统版本： 4.3-6.0
        else if (SupportCheck.isUnderAndroid6_0()) {
            Log.e("Permission", "activity(特殊处理国产机型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final ArrayList<String> againList = new ArrayList<>();
            for (String name : requestList) {
                // if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), name))
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), name) == PackageManager.PERMISSION_GRANTED)
                    continue;
                againList.add(name);
            }
            if (!againList.isEmpty()) {
                final OnPermissionChangeListener api1 = getPermissionChangeListener();
                if (null != api1) {
                    api1.onAgain(requestCode, requestList);
                } else {
                    final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                    if (null != api2) {
                        api2.onAgain(getActivity(), requestCode, requestList);
                    }
                }
            }
            if (!requestList.isEmpty()) {
                final String[] strings = requestList.toArray(new String[requestList.size()]);
                // ActivityCompat.requestPermissions(getActivity(), strings, requestCode);

                final OnPermissionChangeListener api1 = getPermissionChangeListener();
                final boolean granted = SupportCheck.isGranted(getActivity(), strings[0]);
                if (null != api1) {

                    if (granted) {
                        api1.onSucc(requestCode, Arrays.asList(strings[0]));
                    } else {
                        api1.onFail(requestCode, Arrays.asList(strings[0]));
                    }
                } else {
                    final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                    if (null != api2) {
                        if (granted) {
                            api2.onSucc(getActivity(), requestCode, Arrays.asList(strings[0]));
                        } else {
                            api2.onFail(getActivity(), requestCode, Arrays.asList(strings[0]));
                        }
                    }
                }
            }
        }
        // 系统版本：6.0
        else {
            Log.e("Permission", "activity(通用规则权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final ArrayList<String> againList = new ArrayList<>();
            for (String name : requestList) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), name))
                    continue;
                againList.add(name);
            }
            if (!againList.isEmpty()) {
                final OnPermissionChangeListener api1 = getPermissionChangeListener();
                if (null != api1) {
                    api1.onAgain(requestCode, requestList);
                } else {
                    final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                    if (null != api2) {
                        api2.onAgain(getActivity(), requestCode, requestList);
                    }
                }
            }
            if (!requestList.isEmpty()) {
                final String[] strings = requestList.toArray(new String[requestList.size()]);
                ActivityCompat.requestPermissions(getActivity(), strings, requestCode);
            }
        }
    }
}
