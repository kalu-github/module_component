package lib.kalu.core.http.error;

import lib.kalu.context.BaseConstant;

/**
 * description: 数据异常
 * created by kalu on 2018/4/27 11:44
 */
public class HttpException extends RuntimeException {

    private String msg = BaseConstant.NULL_STR;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
