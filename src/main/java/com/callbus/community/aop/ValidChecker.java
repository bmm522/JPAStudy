package com.callbus.community.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidChecker {

    @Around("execution(* com.callbus.community.controller..*.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : proceedingJoinPoint.getArgs()) {


            if (arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()){

                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()){

                        errorMap.put(error.getField(),error.getDefaultMessage());

                    }

                    throw new RuntimeException(errorMap.toString());
              }

            }
        }
        return proceedingJoinPoint.proceed();
    }
}
