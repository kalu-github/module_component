package permission.kalu.core;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import permission.kalu.core.support.SupportPermission;
import permission.kalu.core.wrapper.WrapperActivity;
import permission.kalu.core.wrapper.WrapperFragmentV4;
import permission.kalu.core.wrapper.WrapperImp;

/**
 * description: 权限管理
 * created by kalu on 2017/12/16 16:06
 */
public final class PermissionManager {

    private static final Map<String, WrapperImp> map = Collections.synchronizedMap(new HashMap<String, WrapperImp>());
    private static final WeakReference<Map<Activity, WrapperImp>> reference = new WeakReference(map);

    public static WrapperImp get(Activity activity) {
        final WrapperActivity wrapperImp = new WrapperActivity(activity);
        map.put(activity.getClass().getSimpleName(), wrapperImp);
        return wrapperImp;
    }

    public static WrapperImp get(android.support.v4.app.Fragment fragment) {
        final WrapperFragmentV4 wrapperImp = new WrapperFragmentV4(fragment);
        map.put(fragment.getActivity().getClass().getSimpleName(), wrapperImp);
        return wrapperImp;
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults) {
        Log.e("Permission", "onRequestPermissionsResult ==> requestCode = " + requestCode + ", permissionSize = " + grantResults.length);

        if (null == activity || null == grantResults || grantResults.length <= 0) return;

        final String name = activity.getClass().getSimpleName();
        if (TextUtils.isEmpty(name)) return;

        final WrapperImp wrapperImp = reference.get().get(name);
        if (null == wrapperImp) return;

        final ArrayList<String> succList = new ArrayList<>();
        final ArrayList<String> failList = new ArrayList<>();
        final List<String> sumList = wrapperImp.getPermission();

        for (int i = 0; i < sumList.size(); i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                succList.add(sumList.get(i));
            } else {
                failList.add(sumList.get(i));
            }
        }

        if (succList.size() > 0) {
            SupportPermission.premissionSucc(wrapperImp, succList);
        }

        if (failList.size() > 0) {
            SupportPermission.premissionFail(wrapperImp, failList);
        }
    }
}
