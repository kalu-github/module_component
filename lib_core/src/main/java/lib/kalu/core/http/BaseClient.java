package lib.kalu.core.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lib.demo.util.LogUtil;
import lib.demo.util.NetUtil;
import lib.kalu.core.http.error.HttpException;
import lib.kalu.core.http.listener.OnDownLoadChangeListener;
import lib.kalu.core.http.listener.OnHttpChangeListener;
import lib.kalu.core.http.listener.OnUpLoadChangeListener;
import okhttp3.ResponseBody;

/**
 * description: Http
 * created by kalu on 2017/5/15 10:34
 */
public class BaseClient {

    public static <T> Disposable subscribe(Context context, Observable<HttpResult<T>> observable, OnHttpChangeListener<T> listener) {

        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object -> {

                    if (!object.isSucc()) {
                        listener.onError(object.getMessage());
                        throw new RuntimeException();
                    } else {

                        final T msgDatas = object.getData();
                        if (null == msgDatas) {
                            listener.onError(object.getMessage());
                        }
                        listener.onNext(msgDatas);
                    }
                }, e -> {
                    Log.e("", e.getMessage(), e);

                    if (e instanceof HttpException) {
                        HttpException httpException = (HttpException) e;
                        final String msg = httpException.getMsg();
                        listener.onError(msg);
                    } else if (!NetUtil.isConnected(context)) {
                        listener.onError("请检查网络连接");
                    } else if (e instanceof SocketTimeoutException) {
                        listener.onError("socket连接超时");
                    } else if (e instanceof NoRouteToHostException) {
                        listener.onError("远程服务器异常");
                    } else {
                        listener.onError(e.getMessage());
                    }
                    listener.onComplete();
                }, () -> {
                    if (null == listener) return;
                    LogUtil.e("BaseClient", "subscribe ==> onComplete");
                    listener.onComplete();
                }, disposable -> {
                    if (null == listener) return;
                    LogUtil.e("BaseClient", "subscribe ==> onStart");
                    listener.onStart(disposable);
                });
    }

    /**
     * 单上传文件的封装
     *
     * @param observable
     * @param listener
     */
    @SuppressLint("CheckResult")
    public static void subscribe(Observable<ResponseBody> observable, OnUpLoadChangeListener<ResponseBody> listener) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {

                    if (null != listener) {
                        listener.onUpLoadSuccess(responseBody);
                    }
                }, throwable -> {

                    if (null != listener) {
                        listener.onUpLoadFail(throwable);
                    }
                }, () -> {

                    if (null != listener) {
                        listener.onUpLoadComplete();
                    }
                });
    }

    /**
     * 下载单文件，可以是大文件，该方法不支持断点下载
     */
    @SuppressLint("CheckResult")
    public static void subscribe(Observable<ResponseBody> observable, final String fileDir, final String fileName, final OnDownLoadChangeListener<File> listener) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(responseBody -> listener.saveFile(responseBody, fileDir, fileName))
                .subscribe(file -> {

                    if (null != listener) {
                        listener.onDownLoadSuccess(file);
                    }
                }, throwable -> {

                    if (null != listener) {
                        listener.onDownLoadFail(throwable);
                    }
                }, () -> {

                    if (null != listener) {
                        listener.onDownLoadComplete();
                    }
                });
    }
}