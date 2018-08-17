package permission.kalu.core.platform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * description: google
 * created by kalu on 2018/6/8 8:55
 */
public final class PlatformGoogle implements PlatformImp {
    final Activity activity;

    public PlatformGoogle(Activity activity) {
        this.activity = activity;
    }

    public Intent settingIntent() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(PACKAGE, activity.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }
}
