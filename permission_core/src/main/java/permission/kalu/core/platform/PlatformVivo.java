package permission.kalu.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * description: vivo
 * created by kalu on 2018/6/8 8:56
 */
public class PlatformVivo implements PlatformImp {

    private final String MAIN_CLS = "com.iqoo.secure.MainActivity";
    private final String PKG = "com.iqoo.secure";

    final Activity activity;

    public PlatformVivo(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp = new ComponentName(PKG, MAIN_CLS);

        // starting Intent { flg=0x10000000 cmp=com.iqoo.secure/.safeguard.PurviewTabActivity (has
        // extras) } from ProcessRecord
//        ComponentName comp = new ComponentName(PKG, "com.iqoo.secure.safeguard.PurviewTabActivity");

        // can enter, but blank
//        try {
//            PackageInfo pi = context.getPackageManager().getPackageInfo(PKG,
//                    PackageManager.GET_ACTIVITIES);
//            for (ActivityInfo activityInfo : pi.activities) {
//                Log.e("TAG", "settingIntent:  " + activityInfo.name);
//                if (activityInfo.name.contains(IN_CLS)) {
//                    comp = new ComponentName(PKG, "com.iqoo.secure.safeguard
// .SoftPermissionDetailActivity");
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        intent.setComponent(comp);

        return intent;
    }
}
