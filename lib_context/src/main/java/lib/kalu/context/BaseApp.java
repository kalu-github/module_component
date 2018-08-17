package lib.kalu.context;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.ViewGroup;

/**
 * description: BaseApp
 * created by kalu on 2018/8/15 16:27
 */
public class BaseApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static Context mContext;
    private static ViewGroup mRoot;
    private static Activity mActivity;

    public static ViewGroup getRootView() {
        return mRoot;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(this);
        mContext = getApplicationContext();
    }

    public final static Context getContext() {
        return mContext;
    }

    public final static Resources getResource() {
        return mContext.getResources();
    }

    public final static String getStrings(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    public final static int getColors(int id) {
        return getResource().getColor(id);
    }

    public final static float getDimens(int id) {
        return getResource().getDimension(id);
    }

    public final static Activity getActivity() {
        return mActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mActivity = activity;
        mRoot = activity.findViewById(android.R.id.content);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
