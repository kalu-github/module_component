package demo.prim.com.moudle1;

import android.app.Application;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/7/10 - 下午11:46
 */
public class MoudelApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouterManager.getInstance().initRouter(this);
    }
}
