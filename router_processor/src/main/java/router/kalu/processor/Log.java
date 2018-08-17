package router.kalu.processor;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/8/17 11:41
 */
public final class Log {

    private Messager messager;

    private Log(Messager messager) {
        this.messager = messager;
    }

    public static Log newLog(Messager messager) {
        return new Log(messager);
    }

    public void i(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
