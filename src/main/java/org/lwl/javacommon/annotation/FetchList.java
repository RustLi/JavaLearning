package org.lwl.javacommon.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FetchList {
    String[] tableList() default {};
}
