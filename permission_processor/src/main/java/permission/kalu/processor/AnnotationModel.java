package permission.kalu.processor;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/13 1:03
 */
final class AnnotationModel {

    private String method;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
