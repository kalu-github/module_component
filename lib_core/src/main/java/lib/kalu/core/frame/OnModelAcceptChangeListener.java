package lib.kalu.core.frame;

/**
 * description: 监听变化
 * created by kalu on 2018/4/10 11:47
 */
public interface OnModelAcceptChangeListener<T> {

    default void modelStart() {
    }

    default void modelComplete() {
    }

    default void modelFail() {
    }

    void modelSucc(T result);
}
