package com.qf.elasticsearch.aop;


import java.lang.annotation.*;

/**
 * @Author chenzhongjun
 * 调用方法前后的日志
 * @Date 2020/11/29
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {


}
