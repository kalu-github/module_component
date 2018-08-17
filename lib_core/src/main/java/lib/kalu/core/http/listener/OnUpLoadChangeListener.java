package lib.kalu.core.http.listener;

/**
 * description: 上传文件进度监听
 * created by kalu on 2017/5/12 14:29
 */
public abstract class OnUpLoadChangeListener<T> {


//    @Override
//    public void onNext(T t) {
//        onUpLoadSuccess(t);
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        onUpLoadFail(e);
//    }
//

    /**
     * 可以重写，具体可由子类实现
     */
    public void onUpLoadComplete() {
    }

    public void onProgressChange(long bytesWritten, long contentLength) {
        onUpLoadChange((int) (bytesWritten * 100 / contentLength));
    }

    /**
     * 上传成功的回调
     */
    public abstract void onUpLoadSuccess(T t);

    /**
     * 上传你失败回调
     */
    public abstract void onUpLoadFail(Throwable e);

    /**
     * 上传进度回调
     */
    public abstract void onUpLoadChange(int progress);
}