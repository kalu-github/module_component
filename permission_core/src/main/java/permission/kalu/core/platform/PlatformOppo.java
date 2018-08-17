package permission.kalu.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * description: oppo
 * created by kalu on 2018/6/8 8:56
 */
public class PlatformOppo implements PlatformImp {
    private final String PKG = "com.coloros.safecenter";
    private final String MANAGER_OUT_CLS = "com.coloros.safecenter.permission.singlepage" +
            ".PermissionSinglePageActivity";

    final Activity activity;

    public PlatformOppo(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp;
        comp = new ComponentName(PKG, MANAGER_OUT_CLS);
        // do not work!!
//        comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission" + ".PermissionAppAllPermissionActivity");
        intent.setComponent(comp);

        return intent;
    }
}
