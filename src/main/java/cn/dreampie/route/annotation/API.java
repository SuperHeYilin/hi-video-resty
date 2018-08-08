package cn.dreampie.route.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark a resource class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface API {
    String value();

    boolean ismenu() default false;//是否是功能菜单

    String name() default "";//名称

    String menu_url() default "";//菜单url

    String[] headers() default {};
}
