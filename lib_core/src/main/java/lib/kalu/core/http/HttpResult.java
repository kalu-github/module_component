package lib.kalu.core.http;

import com.google.gson.annotations.Expose;

/**
 * description: 服务器返回数据类型
 * created by kalu on 2018/3/23 8:52
 */
public class HttpResult<T> {

    // 过滤序列化
    @Expose(serialize = false, deserialize = false)
    private final String OK = "0";

    @Expose
    private String show_et_status = null;
    @Expose
    private String message = null;
    @Expose
    private T data = null;

    public void setShow_et_status(String show_et_status) {
        this.show_et_status = show_et_status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucc() {
        return OK.equals(show_et_status);
    }
}