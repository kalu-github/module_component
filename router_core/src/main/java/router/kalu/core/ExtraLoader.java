package router.kalu.core;

import android.app.Activity;
import android.util.LruCache;

import router.kalu.core.interfaces.IExtra;

public final class ExtraLoader {
    public static ExtraLoader extraLoader;

    public static ExtraLoader getInstance() {
        if (extraLoader == null) {
            synchronized (ExtraLoader.class) {
                if (extraLoader == null) {
                    extraLoader = new ExtraLoader();
                }
            }
        }
        return extraLoader;
    }

    private LruCache<String, IExtra> extraLruCache;

    public ExtraLoader() {
        extraLruCache = new LruCache<>(88);
    }


    public void loadExtra(Activity activity) {
        String name = activity.getClass().getName();
        IExtra iExtra = extraLruCache.get(name);
        try {
            if (iExtra == null) {
                iExtra = (IExtra) Class.forName(activity.getClass().getName() + "$$Extra").getConstructor().newInstance();
            }
            iExtra.loadExtra(activity);
            extraLruCache.put(name, iExtra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
