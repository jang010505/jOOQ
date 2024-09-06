package com.seminar.querydsl_sql.globsl.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  // 적용 범위 설정
  @Pointcut("execution(* com.seminar.querydsl_sql..controller.*.*(..))")
  private void api() {}

  @Before("api()")
  public void beforeLog(JoinPoint joinPoint) throws NoSuchMethodException {

    Method method = getMethod(joinPoint);
    log.info("[Before] Method name: " + method.getName());

    Parameter[] parameters = method.getParameters();
    Object[] args = joinPoint.getArgs();

    for (Parameter parameter : parameters) {
      log.info("parameter name: " + parameter.getName());
      log.info("parameter type: " + parameter.getType().getSimpleName());
    }
  }

  @AfterReturning(pointcut = "api()", returning = "result")
  public void afterReturningLog(JoinPoint joinPoint, Object result) throws NoSuchMethodException {

    Method method = getMethod(joinPoint);
    log.info("[AfterReturning] Method name: " + method.getName());

    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      log.info("parameter value: " + arg);
    }


    if (result == null) return;
    log.info("return: " + result);
  }

  private Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
    String methodName = joinPoint.getSignature().getName();
    Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes();
    return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
  }
}
