package lib.kalu.core.frame;

import android.content.Context;

import lib.demo.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import lib.kalu.core.http.BaseClient;
import lib.kalu.core.http.listener.OnHttpChangeListener;

/**
 * description: MVP ==> V接口
 * created by kalu on 2018/3/22 9:16
 */
public final class BaseModel {

    private final String TAG = BaseModel.class.getName();

    <T> void request(Context context, Observable observable, OnModelAcceptChangeListener<T> listener) {

        if(null == observable)
            return;

        BaseClient.subscribe(context, observable, new OnHttpChangeListener<T>() {
            @Override
            public void onStart(Disposable disposable) {
                listener.modelStart();
            }

            @Override
            public void onNext(T result) {
                listener.modelSucc(result);
            }

            @Override
            public void onError(String msg) {
                LogUtil.e(TAG, msg);
                listener.modelFail();
                listener.modelComplete();
            }

            @Override
            public void onComplete() {
                listener.modelComplete();
            }
        });
    }

    void request(Context context, Observable observable, OnModelChangeListener listener) {

        if(null == observable)
            return;

        BaseClient.subscribe(context, observable, new OnHttpChangeListener<Object>() {
            @Override
            public void onStart(Disposable disposable) {
                listener.modelStart();
            }

            @Override
            public void onNext(Object result) {
                listener.modelSucc();
            }

            @Override
            public void onError(String msg) {
                LogUtil.e(TAG, msg);
                listener.modelFail();
                listener.modelComplete();
            }

            @Override
            public void onComplete() {
                listener.modelComplete();
            }
        });
    }
}