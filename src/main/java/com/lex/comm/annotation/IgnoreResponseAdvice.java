package com.lex.comm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要忽略统一JSON返回的注解
 */
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
    /**
     * 是否进行全局异常处理封装
     *
     * @return true:进行处理;  false:不进行异常处理
     */
    boolean errorDispose() default true;
}
