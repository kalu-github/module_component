package permission.kalu.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import permission.kalu.core.support.SupportCheck;

/**
 * description: meizu
 * created by kalu on 2018/6/8 8:56
 */
public class PlatformMeizu implements PlatformImp {

    private final String N_MANAGER_OUT_CLS = "com.meizu.safe.permission.PermissionMainActivity";
    private final String L_MANAGER_OUT_CLS = "com.meizu.safe.SecurityMainActivity";
    private final String PKG = "com.meizu.safe";

    final Activity activity;

    public PlatformMeizu(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp = new ComponentName(PKG, getCls());
        intent.setComponent(comp);

        return intent;
    }

    private String getCls() {
        if (SupportCheck.isUnderAndroid6_0()) {
            return L_MANAGER_OUT_CLS;
        } else {
            return N_MANAGER_OUT_CLS;
        }
    }
}
