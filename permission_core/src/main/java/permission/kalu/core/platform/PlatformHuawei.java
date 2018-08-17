package permission.kalu.core.platform;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * description: huawei
 * created by kalu on 2018/6/8 8:55
 */
public class PlatformHuawei  implements PlatformImp {
    private final String PKG = "com.huawei.systemmanager";
    private final String MANAGER_OUT_CLS = "com.huawei.permissionmanager.ui.MainActivity";
//    private final String SINGLE_CLS = "com.huawei.permissionmanager.ui.SingleAppActivity";
//    private final String SINGLE_TAG = "SingleAppActivity";

    final Activity activity;

    public PlatformHuawei(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() throws ActivityNotFoundException {
        Intent intent = new PlatformGoogle(activity).settingIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp = null;
        try {
            PackageInfo pi = activity.getPackageManager().getPackageInfo(PKG,
                    PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : pi.activities) {
                if (activityInfo.name.equals(MANAGER_OUT_CLS)) {
                    comp = new ComponentName(PKG, MANAGER_OUT_CLS);
                }
            }
            if (comp != null) {
                intent.setComponent(comp);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return intent;
        }

        return intent;

        // need "com.huawei.systemmanager.permission.ACCESS_INTERFACE" permission
//        try {
//            PackageInfo pi = context.getPackageManager().getPackageInfo(PKG,
//                    PackageManager.GET_ACTIVITIES);
//            for (ActivityInfo activityInfo : pi.activities) {
//                if (activityInfo.name.contains(SINGLE_TAG)) {
//                    comp = new ComponentName(PKG, SINGLE_CLS);
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
