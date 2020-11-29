package com.qf.elasticsearch.aop;

import com.qf.elasticsearch.util.JSONUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author chenzhongjun
 * @Date 2020/11/29
 */
@Component
@Aspect
public class MethodLogAspect {

    private Logger logger = LoggerFactory.getLogger(MethodLogAspect.class.getName());

    @Around("@annotation(MethodLog)")
    public Object methodLog(ProceedingJoinPoint pjp){
        Object proceed = null;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        logger.info("{} invoked. args:{}", method.getName(), JSONUtils.objectToJson(pjp.getArgs()));
        try {
            proceed = pjp.proceed();
        } catch (Throwable throwable) {
            logger.error("methodLog annotation error ", throwable.getCause());
        }
        logger.info("{} end. result:{}", method.getName(), JSONUtils.objectToJson(proceed));
        return proceed;
    }
}
