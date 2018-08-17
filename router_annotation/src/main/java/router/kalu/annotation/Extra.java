package router.kalu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/8/17 10:56
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Extra {
    String name() default "";
}
