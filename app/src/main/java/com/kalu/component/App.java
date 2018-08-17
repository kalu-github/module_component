package com.kalu.component;

import router.kalu.core.RouterManager;
import com.tencent.smtt.sdk.QbSdk;

import lib.demo.util.LogUtil;
import lib.kalu.context.BaseApp;

public final class App extends BaseApp {

    private final String TAG = BaseApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        // 路由
        initRouter();

        // X5
        initX5();
    }

    /**********************************************************************************************/

    private final void initRouter() {

        RouterManager.getInstance().initRouter(this, new RouterManager.OnRouterChangeListener() {
            @Override
            public void onRouterSucc() {
                LogUtil.e(TAG, "onRouterSucc[路由] ==> ");
            }

            @Override
            public void onRouterFail(Exception e) {
                LogUtil.e(TAG, "onRouterFail[路由] ==> msg = " + e.getMessage(), e);
                initRouter();
            }
        });
    }

    private final void initX5() {

        final long begin = System.currentTimeMillis();
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtil.e(TAG, "onCoreInitFinished[X5] ==> ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                final long end = System.currentTimeMillis();
                LogUtil.e(TAG, "onViewInitFinished[X5] ==> result = " + b + ", time = " + (end - begin) + "ms");
                if (b) return;
                initX5();
            }
        });
    }
}
