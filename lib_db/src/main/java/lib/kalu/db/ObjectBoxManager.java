package lib.kalu.db;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import io.objectbox.reactive.ErrorObserver;
import lib.kalu.context.BaseApp;
import lib.kalu.db.model.Http_;
import lib.kalu.db.model.MyObjectBox;

public final class ObjectBoxManager {

    /*************************************   创建单例   *********************************************/

    private BoxStore mBoxStore;
    private final DataSubscriptionList subscriptions = new DataSubscriptionList();


    private static class SingletonHolder {
        private final static ObjectBoxManager instance = new ObjectBoxManager();
    }

    public static ObjectBoxManager getInstance() {
        return SingletonHolder.instance;
    }

    private ObjectBoxManager() {
        mBoxStore = MyObjectBox.builder().androidContext(BaseApp.getContext()).build();
    }

    /*********************************************************************/

    public interface OnBoxChangeListsner<T> {

        void onChange(List<T> data);
    }

    /*********************************************************************/

    public final BoxStore getBoxStore() {
        return mBoxStore;
    }

    /*********************************************************************/

    public final <T> void queryAllAsyn(Class<T> cls, final OnBoxChangeListsner<T> listsner) {

        final Query<T> query = mBoxStore.boxFor(cls).query().build();
        query.subscribe(subscriptions).single().on(AndroidScheduler.mainThread())
                .onError(new ErrorObserver() {
                    @Override
                    public void onError(Throwable th) {

                    }
                })
                .observer(new DataObserver<List<T>>() {
                    @Override
                    public void onData(List<T> data) {
                        listsner.onChange(data);
                    }
                });
    }

    public final <T> List<T> queryAll(Class<T> cls) {

        final Query<T> query = mBoxStore.boxFor(cls).query().build();
        return query.find();
    }

    public final <T> T query(Class<T> cls, String url) {

        if (null == mBoxStore)
            return null;

        final Box<T> tBox = mBoxStore.boxFor(cls);
        if (null == tBox)
            return null;

        final List<T> ts = tBox.find(Http_.url, url);
        if (null == ts || ts.isEmpty())
            return null;

        return ts.get(0);
    }

    public final <T> boolean insert(Class<T> cls, T model) {

        if (null == mBoxStore)
            return false;

        final Box<T> tBox = mBoxStore.boxFor(cls);
        if (null == tBox)
            return false;

        tBox.put(model);
        return true;
    }

    public final <T> T remove(Class<T> cls, String url) {

        final T query = query(cls, url);
        if (null == query)
            return null;

        final Box<T> tBox = mBoxStore.boxFor(cls);
        tBox.remove(query);
        return query;
    }
}
