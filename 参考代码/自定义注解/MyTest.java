package com.itheima.a_annotation.demo3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解
 * @author admin
 *
 */
@Retention(value=RetentionPolicy.RUNTIME)//这个元注解让MyTest注解在整个运行时期都有效。Retention枚举
public @interface MyTest {

}
