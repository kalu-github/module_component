package lib.kalu.core.http.listener;

import io.reactivex.disposables.Disposable;
import lib.demo.util.LogUtil;

/**
 * description: 网络结果监听
 * created by kalu on 2017/5/15 10:34
 */
public abstract class OnSimpleHttpChangeListener<T> implements OnHttpChangeListener<T> {

    private final String TAG = OnSimpleHttpChangeListener.class.getName();

    @Override
    public void onStart(Disposable disposable) {
    }

    @Override
    public void onNext(T result) {
        onSucc(result);
    }

    @Override
    public void onError(String msg) {
        LogUtil.e(TAG, msg);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSucc(T result);
}
