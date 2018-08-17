package lib.kalu.core.http.listener;

import io.reactivex.disposables.Disposable;

/**
 * description: 网络结果监听
 * created by kalu on 2017/5/15 10:34
 */
public interface OnHttpChangeListener<T> {

    /**
     * 开始
     */
    void onStart(Disposable disposable);

    /**
     * 成功
     */
    void onNext(T result);

    /**
     * 失败
     */
    void onError(String msg);

    /**
     * 网络请求完成, onSuccess - onError - 最后都会调用
     */
    void onComplete();
}
