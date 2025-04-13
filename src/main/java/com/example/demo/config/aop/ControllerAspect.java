package com.example.demo.config.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Log4j2
public class ControllerAspect {

    public static final String REQUEST_TOTAL_TIME = "request-total-time";
    public static final String REQUEST_START_TIME = "request-start-time";
    public static final String REQUEST_END_TIME = "request-end-time";

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void controllerPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before(value = "controllerPackagePointcut()")
    public void beforeAdvice(JoinPoint joinPoint) throws JsonProcessingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        final StopWatch stopWatch = StopWatch.createStarted();
        request.setAttribute(REQUEST_TOTAL_TIME, stopWatch);

        // 放入 Session ID
        MDC.put("sessionId", UUID.randomUUID().toString());

        log.info("======================================================================");
        log.info("URL              : {}", request.getRequestURL().toString());
        log.info("IP               : {}", request.getRemoteAddr());
        log.info("HTTP_METHOD      : {}", request.getMethod());
        log.info("CLASS_METHOD     : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("controllerPackagePointcut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 接收到請求時，計下開始時間
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        // Measure method execution time
        Object result = proceedingJoinPoint.proceed();
        StopWatch stopWatch = (StopWatch) request.getAttribute(REQUEST_TOTAL_TIME);
        // Log method execution time
        log.info(String.format("Execution time of %s.%s with :: %s ms",
                        className,
                        methodName,
                        stopWatch.getTime(TimeUnit.MILLISECONDS)
                )
        );
        return result;
    }

    @AfterReturning(value = "controllerPackagePointcut()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, ResponseEntity<?> result) throws JsonProcessingException {
        // 請求處理完後，紀錄處理結果;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        StopWatch stopWatch = (StopWatch) request.getAttribute(REQUEST_TOTAL_TIME);
        stopWatch.stop();
    }
}
