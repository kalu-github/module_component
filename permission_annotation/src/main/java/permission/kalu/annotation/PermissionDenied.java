package permission.kalu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 用户已经点击拒绝提示, 默认调转权限设置页面
 * created by kalu on 2017/12/16 17:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface PermissionDenied {
    int value();
}
