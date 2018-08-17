package permission.kalu.core.platform;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description: mi
 * created by kalu on 2018/6/8 8:56
 */
public class PlatformMi implements PlatformImp {
    private final String PKG = "com.miui.securitycenter";
    // manager
    private final String MIUI8_MANAGER_OUT_CLS = "com.miui.securityscan.MainActivity";
    private final String MIUI7_MANAGER_OUT_CLS = "com.miui.permcenter.permissions" +
            ".AppPermissionsEditorActivity";
    // xiaomi permissions setting page
    private final String MIUI8_OUT_CLS = "com.android.settings.applications.InstalledAppDetailsTop";

    final Activity activity;

    public PlatformMi(Activity activity) {
        this.activity = activity;
    }

    private static String getSystemProperty() {
        String line = "";
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    @Override
    public Intent settingIntent() throws ActivityNotFoundException {
        Intent intent = new Intent();
        String miuiInfo = getSystemProperty();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (miuiInfo.contains("7") || miuiInfo.contains("6")) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName(PKG, MIUI7_MANAGER_OUT_CLS);
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else {
            // miui 8
            intent.putExtra(PACKAGE, activity.getPackageName());
            ComponentName comp = new ComponentName(PKG, MIUI8_MANAGER_OUT_CLS);
            intent.setComponent(comp);
        }

        return intent;
    }
}
