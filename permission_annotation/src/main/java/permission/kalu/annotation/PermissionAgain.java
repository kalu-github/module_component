package permission.kalu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 第二次提醒, 用户没有点击拒绝提示, 系统默认提示框
 * created by kalu on 2017/12/16 17:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface PermissionAgain {
    int value();
}
